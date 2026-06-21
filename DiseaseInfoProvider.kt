package com.example.plantdetector.ml

/**
 * Holds display name, plant name, and care tips for each class the
 * TFLite model can output. Update the keys to exactly match the strings
 * in assets/labels.txt (PlantVillage dataset format: "Plant___Disease").
 */
data class DiseaseInfo(
    val displayName: String,
    val plantName: String,
    val isHealthy: Boolean,
    val careTips: List<String>
)

object DiseaseInfoProvider {

    private val infoMap: Map<String, DiseaseInfo> = mapOf(

        "Apple___Apple_scab" to DiseaseInfo(
            displayName = "Apple Scab",
            plantName = "Apple",
            isHealthy = false,
            careTips = listOf(
                "Remove and destroy fallen infected leaves to reduce spore spread.",
                "Apply a fungicide containing captan or myclobutanil in early spring.",
                "Prune trees to improve air circulation around leaves.",
                "Avoid overhead watering — water at the base instead."
            )
        ),
        "Apple___Black_rot" to DiseaseInfo(
            displayName = "Black Rot",
            plantName = "Apple",
            isHealthy = false,
            careTips = listOf(
                "Prune out dead or cankered branches during dry weather.",
                "Remove mummified fruit left on the tree or ground.",
                "Apply fungicide sprays starting at green tip stage.",
                "Maintain good orchard sanitation throughout the season."
            )
        ),
        "Apple___Cedar_apple_rust" to DiseaseInfo(
            displayName = "Cedar Apple Rust",
            plantName = "Apple",
            isHealthy = false,
            careTips = listOf(
                "Remove nearby cedar/juniper trees if possible — they host the fungus.",
                "Apply protectant fungicide from pink bud stage through petal fall.",
                "Choose rust-resistant apple varieties for new plantings.",
                "Rake and dispose of fallen infected leaves each autumn."
            )
        ),
        "Apple___healthy" to DiseaseInfo(
            displayName = "Healthy",
            plantName = "Apple",
            isHealthy = true,
            careTips = listOf(
                "Your apple plant looks healthy! Keep up consistent watering.",
                "Continue routine pruning to maintain good airflow.",
                "Monitor monthly for early signs of pests or disease.",
                "Fertilize in early spring with a balanced fruit-tree fertilizer."
            )
        ),
        "Corn_(maize)___Common_rust_" to DiseaseInfo(
            displayName = "Common Rust",
            plantName = "Corn",
            isHealthy = false,
            careTips = listOf(
                "Plant rust-resistant corn hybrids in future seasons.",
                "Apply a foliar fungicide if rust appears before tasseling.",
                "Avoid dense planting to improve air circulation.",
                "Rotate crops to reduce fungal spore buildup in soil."
            )
        ),
        "Corn_(maize)___Northern_Leaf_Blight" to DiseaseInfo(
            displayName = "Northern Leaf Blight",
            plantName = "Corn",
            isHealthy = false,
            careTips = listOf(
                "Rotate with non-host crops like soybeans for 1-2 seasons.",
                "Till under crop residue after harvest to reduce spores.",
                "Use resistant hybrid varieties where available.",
                "Apply fungicide at first sign of lesions on lower leaves."
            )
        ),
        "Corn_(maize)___healthy" to DiseaseInfo(
            displayName = "Healthy",
            plantName = "Corn",
            isHealthy = true,
            careTips = listOf(
                "Your corn plant looks healthy! Maintain regular watering.",
                "Side-dress with nitrogen fertilizer during rapid growth.",
                "Watch for pest damage like corn borers as it matures.",
                "Ensure adequate spacing for good airflow between plants."
            )
        ),
        "Potato___Early_blight" to DiseaseInfo(
            displayName = "Early Blight",
            plantName = "Potato",
            isHealthy = false,
            careTips = listOf(
                "Remove and destroy infected lower leaves immediately.",
                "Apply chlorothalonil or copper-based fungicide weekly.",
                "Avoid overhead irrigation — keep foliage dry.",
                "Rotate potato crops with non-solanaceous plants yearly."
            )
        ),
        "Potato___Late_blight" to DiseaseInfo(
            displayName = "Late Blight",
            plantName = "Potato",
            isHealthy = false,
            careTips = listOf(
                "Act fast — late blight spreads rapidly in cool, wet weather.",
                "Remove and destroy all infected plants, do not compost.",
                "Apply a systemic fungicide containing metalaxyl.",
                "Avoid planting near tomatoes, which share this pathogen."
            )
        ),
        "Potato___healthy" to DiseaseInfo(
            displayName = "Healthy",
            plantName = "Potato",
            isHealthy = true,
            careTips = listOf(
                "Your potato plant looks healthy! Hill soil around stems as it grows.",
                "Water consistently — avoid letting soil fully dry out.",
                "Watch for early signs of blight especially in humid weather.",
                "Fertilize with balanced NPK at planting and mid-season."
            )
        ),
        "Tomato___Bacterial_spot" to DiseaseInfo(
            displayName = "Bacterial Spot",
            plantName = "Tomato",
            isHealthy = false,
            careTips = listOf(
                "Avoid working with plants when leaves are wet.",
                "Apply copper-based bactericide early in the season.",
                "Remove and destroy infected plant debris after harvest.",
                "Use disease-free certified seeds or transplants next season."
            )
        ),
        "Tomato___Early_blight" to DiseaseInfo(
            displayName = "Early Blight",
            plantName = "Tomato",
            isHealthy = false,
            careTips = listOf(
                "Remove lower infected leaves and improve air circulation.",
                "Mulch around the base to prevent soil splash onto leaves.",
                "Apply fungicide containing chlorothalonil or copper.",
                "Stake or cage plants to keep foliage off the ground."
            )
        ),
        "Tomato___Late_blight" to DiseaseInfo(
            displayName = "Late Blight",
            plantName = "Tomato",
            isHealthy = false,
            careTips = listOf(
                "Remove and destroy infected plants immediately — highly contagious.",
                "Apply a preventative fungicide before symptoms appear in wet seasons.",
                "Avoid overhead watering, especially in the evening.",
                "Do not plant tomatoes in the same spot as last year's infected crop."
            )
        ),
        "Tomato___Leaf_Mold" to DiseaseInfo(
            displayName = "Leaf Mold",
            plantName = "Tomato",
            isHealthy = false,
            careTips = listOf(
                "Increase ventilation, especially in greenhouses or humid areas.",
                "Reduce humidity by spacing plants further apart.",
                "Apply a fungicide labeled for leaf mold control.",
                "Remove and discard affected leaves promptly."
            )
        ),
        "Tomato___Septoria_leaf_spot" to DiseaseInfo(
            displayName = "Septoria Leaf Spot",
            plantName = "Tomato",
            isHealthy = false,
            careTips = listOf(
                "Remove infected leaves as soon as spots appear.",
                "Mulch heavily to prevent soil-borne spores splashing up.",
                "Apply fungicide containing copper or chlorothalonil.",
                "Avoid handling wet plants to prevent spreading spores."
            )
        ),
        "Tomato___Spider_mites Two-spotted_spider_mite" to DiseaseInfo(
            displayName = "Spider Mites",
            plantName = "Tomato",
            isHealthy = false,
            careTips = listOf(
                "Spray plants with water to dislodge mites from leaves.",
                "Apply insecticidal soap or neem oil every 5-7 days.",
                "Introduce predatory mites as a natural biological control.",
                "Avoid excessive nitrogen fertilizer, which attracts mites."
            )
        ),
        "Tomato___Target_Spot" to DiseaseInfo(
            displayName = "Target Spot",
            plantName = "Tomato",
            isHealthy = false,
            careTips = listOf(
                "Improve air circulation by pruning dense foliage.",
                "Apply a broad-spectrum fungicide at first symptoms.",
                "Avoid overhead irrigation — water at soil level.",
                "Remove crop debris at the end of the season."
            )
        ),
        "Tomato___Tomato_Yellow_Leaf_Curl_Virus" to DiseaseInfo(
            displayName = "Yellow Leaf Curl Virus",
            plantName = "Tomato",
            isHealthy = false,
            careTips = listOf(
                "Control whiteflies, the primary carrier of this virus.",
                "Remove and destroy infected plants to prevent spread.",
                "Use reflective mulch to repel whiteflies from plants.",
                "Plant virus-resistant tomato varieties going forward."
            )
        ),
        "Tomato___Tomato_mosaic_virus" to DiseaseInfo(
            displayName = "Mosaic Virus",
            plantName = "Tomato",
            isHealthy = false,
            careTips = listOf(
                "Remove and destroy infected plants — there is no cure.",
                "Wash hands and tools after handling infected plants.",
                "Avoid using tobacco products near plants (can carry virus).",
                "Choose mosaic-virus-resistant tomato varieties next season."
            )
        ),
        "Tomato___healthy" to DiseaseInfo(
            displayName = "Healthy",
            plantName = "Tomato",
            isHealthy = true,
            careTips = listOf(
                "Your tomato plant looks healthy! Keep up consistent watering.",
                "Stake or cage plants to support fruit-bearing branches.",
                "Fertilize every 2-3 weeks during the growing season.",
                "Monitor weekly for early signs of pests or disease."
            )
        )
    )

    private val fallback = DiseaseInfo(
        displayName = "Unknown",
        plantName = "Unidentified Plant",
        isHealthy = false,
        careTips = listOf(
            "We couldn't confidently identify this condition.",
            "Try retaking the photo in good natural lighting.",
            "Make sure only a single leaf fills most of the frame.",
            "Consult a local agricultural extension office for help."
        )
    )

    fun getInfo(rawLabel: String): DiseaseInfo =
        infoMap[rawLabel] ?: fallback
}
