package com.example.plantdetector.ml

import android.content.Context
import android.graphics.Bitmap
import android.graphics.RectF
import org.tensorflow.lite.DataType
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.common.FileUtil
import org.tensorflow.lite.support.common.ops.NormalizeOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.image.ops.Rot90Op
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.IOException

data class ClassificationResult(
    val rawLabel: String,
    val confidence: Float
)

class PlantDiseaseClassifier(context: Context) {

    companion object {
        private const val MODEL_PATH = "plant_disease_model.tflite"
        private const val LABELS_PATH = "labels.txt"
        private const val INPUT_SIZE = 224          // Model expects 224x224 input
        private const val IMAGE_MEAN = 0f
        private const val IMAGE_STD = 255f           // Normalizes pixel values to [0,1]
    }

    private var interpreter: Interpreter? = null
    private var labels: List<String> = emptyList()
    private var isInitialized = false

    init {
        try {
            val model = FileUtil.loadMappedFile(context, MODEL_PATH)
            val options = Interpreter.Options().apply {
                setNumThreads(4)
            }
            interpreter = Interpreter(model, options)
            labels = FileUtil.loadLabels(context, LABELS_PATH)
            isInitialized = true
        } catch (e: IOException) {
            isInitialized = false
            e.printStackTrace()
        }
    }

    fun isReady(): Boolean = isInitialized

    /**
     * Runs inference on a Bitmap and returns the top prediction.
     * Should be called from a background thread (Dispatchers.Default).
     */
    fun classify(bitmap: Bitmap, rotationDegrees: Int = 0): ClassificationResult? {
        val currentInterpreter = interpreter ?: return null
        if (labels.isEmpty()) return null

        // 1. Preprocess: resize to 224x224, rotate if needed, normalize to [0,1]
        val imageProcessor = ImageProcessor.Builder()
            .add(ResizeOp(INPUT_SIZE, INPUT_SIZE, ResizeOp.ResizeMethod.BILINEAR))
            .add(Rot90Op(-rotationDegrees / 90))
            .add(NormalizeOp(IMAGE_MEAN, IMAGE_STD))
            .build()

        var tensorImage = TensorImage(DataType.FLOAT32)
        tensorImage.load(bitmap)
        tensorImage = imageProcessor.process(tensorImage)

        // 2. Prepare output buffer matching the model's output shape
        val outputShape = currentInterpreter.getOutputTensor(0).shape()
        val outputBuffer = TensorBuffer.createFixedSize(outputShape, DataType.FLOAT32)

        // 3. Run inference
        currentInterpreter.run(tensorImage.buffer, outputBuffer.buffer.rewind())

        // 4. Find the highest-confidence class
        val scores = outputBuffer.floatArray
        var maxIndex = 0
        var maxScore = scores[0]
        for (i in scores.indices) {
            if (scores[i] > maxScore) {
                maxScore = scores[i]
                maxIndex = i
            }
        }

        if (maxIndex >= labels.size) return null

        return ClassificationResult(
            rawLabel = labels[maxIndex],
            confidence = maxScore
        )
    }

    /**
     * Returns top-N predictions sorted by confidence, useful for showing
     * alternative possibilities when the top result has low confidence.
     */
    fun classifyTopN(bitmap: Bitmap, n: Int = 3, rotationDegrees: Int = 0): List<ClassificationResult> {
        val currentInterpreter = interpreter ?: return emptyList()
        if (labels.isEmpty()) return emptyList()

        val imageProcessor = ImageProcessor.Builder()
            .add(ResizeOp(INPUT_SIZE, INPUT_SIZE, ResizeOp.ResizeMethod.BILINEAR))
            .add(Rot90Op(-rotationDegrees / 90))
            .add(NormalizeOp(IMAGE_MEAN, IMAGE_STD))
            .build()

        var tensorImage = TensorImage(DataType.FLOAT32)
        tensorImage.load(bitmap)
        tensorImage = imageProcessor.process(tensorImage)

        val outputShape = currentInterpreter.getOutputTensor(0).shape()
        val outputBuffer = TensorBuffer.createFixedSize(outputShape, DataType.FLOAT32)
        currentInterpreter.run(tensorImage.buffer, outputBuffer.buffer.rewind())

        val scores = outputBuffer.floatArray
        return scores.indices
            .map { ClassificationResult(labels.getOrElse(it) { "Unknown" }, scores[it]) }
            .sortedByDescending { it.confidence }
            .take(n)
    }

    fun close() {
        interpreter?.close()
        interpreter = null
    }
}
