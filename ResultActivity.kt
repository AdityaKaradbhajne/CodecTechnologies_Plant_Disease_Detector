package com.example.plantdetector.ui.result

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.plantdetector.R

class ResultActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DISEASE_NAME = "extra_disease_name"
        const val EXTRA_PLANT_NAME = "extra_plant_name"
        const val EXTRA_CONFIDENCE = "extra_confidence"
        const val EXTRA_IS_HEALTHY = "extra_is_healthy"
        const val EXTRA_CARE_TIPS = "extra_care_tips"
        const val EXTRA_IMAGE_URI = "extra_image_uri"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }

        val diseaseName = intent.getStringExtra(EXTRA_DISEASE_NAME) ?: "Unknown"
        val plantName = intent.getStringExtra(EXTRA_PLANT_NAME) ?: "Unknown plant"
        val confidence = intent.getFloatExtra(EXTRA_CONFIDENCE, 0f)
        val isHealthy = intent.getBooleanExtra(EXTRA_IS_HEALTHY, false)
        val careTips = intent.getStringArrayListExtra(EXTRA_CARE_TIPS) ?: arrayListOf()

        val ivStatusIcon = findViewById<ImageView>(R.id.ivStatusIcon)
        val tvDiseaseName = findViewById<TextView>(R.id.tvDiseaseName)
        val tvPlantName = findViewById<TextView>(R.id.tvPlantName)
        val tvConfidence = findViewById<TextView>(R.id.tvConfidence)
        val tipsContainer = findViewById<LinearLayout>(R.id.tipsContainer)
        val statusBanner = findViewById<LinearLayout>(R.id.statusBanner)
        val btnScanAnother = findViewById<Button>(R.id.btnScanAnother)

        tvDiseaseName.text = diseaseName
        tvPlantName.text = plantName
        tvConfidence.text = getString(R.string.confidence_format, (confidence * 100).toInt())

        if (isHealthy) {
            ivStatusIcon.setImageResource(R.drawable.ic_check_circle)
            statusBanner.setBackgroundResource(R.drawable.bg_banner_healthy)
        } else {
            ivStatusIcon.setImageResource(R.drawable.ic_warning)
            statusBanner.setBackgroundResource(R.drawable.bg_banner_warning)
        }

        // Build care tips list dynamically
        tipsContainer.removeAllViews()
        careTips.forEachIndexed { index, tip ->
            val tipView = layoutInflater.inflate(R.layout.item_care_tip, tipsContainer, false)
            tipView.findViewById<TextView>(R.id.tvTipNumber).text = "${index + 1}"
            tipView.findViewById<TextView>(R.id.tvTipText).text = tip
            tipsContainer.addView(tipView)
        }

        btnScanAnother.setOnClickListener { finish() }
    }
}
