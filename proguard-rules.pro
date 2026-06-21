# Keep Room entities
-keep class com.example.plantdetector.data.** { *; }

# Keep TensorFlow Lite
-keep class org.tensorflow.lite.** { *; }
-keep class org.tensorflow.lite.support.** { *; }

# Keep model classes
-keep class com.example.plantdetector.ml.** { *; }

# General Android rules
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable
