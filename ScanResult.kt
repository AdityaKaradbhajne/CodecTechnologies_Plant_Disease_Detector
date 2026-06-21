package com.example.plantdetector.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "scan_results")
data class ScanResult(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val imageUri: String,           // Local content:// or file:// URI of the scanned image
    val diseaseName: String,        // e.g. "Tomato — Early Blight"
    val plantName: String,          // e.g. "Tomato"
    val confidence: Float,          // 0.0 - 1.0
    val isHealthy: Boolean,
    val careTips: String,           // Newline-separated remedy/care tips
    val timestamp: Long = System.currentTimeMillis()
)
