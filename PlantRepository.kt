package com.example.plantdetector.data

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import com.example.plantdetector.ml.ClassificationResult
import com.example.plantdetector.ml.DiseaseInfoProvider
import com.example.plantdetector.ml.PlantDiseaseClassifier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

data class ScanOutcome(
    val classification: ClassificationResult,
    val displayName: String,
    val plantName: String,
    val isHealthy: Boolean,
    val careTips: List<String>
)

class PlantRepository(
    private val dao: ScanResultDao,
    context: Context
) {
    private val classifier = PlantDiseaseClassifier(context.applicationContext)

    fun isClassifierReady(): Boolean = classifier.isReady()

    // ── Room helpers ──────────────────────────────────────────────────────────

    fun getAllScans(): Flow<List<ScanResult>> = dao.getAllScans()

    suspend fun getScanById(id: Long): ScanResult? = dao.getScanById(id)

    suspend fun deleteScan(id: Long) = dao.deleteScan(id)

    suspend fun deleteAllScans() = dao.deleteAllScans()

    fun getDiseasedCount(): Flow<Int> = dao.getDiseasedCount()

    // ── Classification + persistence ─────────────────────────────────────────

    /**
     * Runs TFLite inference on a background thread, maps the result to
     * human-readable info, and saves it to Room. Returns the full outcome
     * for immediate display on the results screen.
     */
    suspend fun classifyAndSave(bitmap: Bitmap, imageUri: Uri, rotationDegrees: Int = 0): ScanOutcome? =
        withContext(Dispatchers.Default) {
            val result = classifier.classify(bitmap, rotationDegrees) ?: return@withContext null
            val info = DiseaseInfoProvider.getInfo(result.rawLabel)

            val scan = ScanResult(
                imageUri = imageUri.toString(),
                diseaseName = info.displayName,
                plantName = info.plantName,
                confidence = result.confidence,
                isHealthy = info.isHealthy,
                careTips = info.careTips.joinToString("\n")
            )
            dao.insertScan(scan)

            ScanOutcome(
                classification = result,
                displayName = info.displayName,
                plantName = info.plantName,
                isHealthy = info.isHealthy,
                careTips = info.careTips
            )
        }

    fun close() = classifier.close()
}
