# 🌿 AI-Based Plant Disease Detector — Android App

An on-device plant disease classifier built with **Kotlin**, **TensorFlow Lite**, **CameraX**, and **Room Database**, developed as part of the Codec Technologies Android Internship.

---

## ✨ Features

| Feature | Details |
|---|---|
| 📷 Camera Scan | Live CameraX preview with one-tap capture |
| 🖼️ Gallery Upload | Pick existing photos from your gallery |
| 🧠 On-Device AI | TensorFlow Lite inference — works fully offline |
| 💊 Care Tips | Disease-specific remedies and prevention tips |
| 📊 Confidence Score | Shows model's prediction confidence percentage |
| 🗂️ Scan History | All past scans saved locally with Room DB |
| ✅ Healthy Detection | Distinguishes healthy plants from diseased ones |

---

## 🏗️ Architecture

```
MVVM + Repository Pattern
─────────────────────────────────────────────────
 UI Layer       →  MainActivity (camera), ResultActivity, HistoryActivity
 Adapters       →  ScanHistoryAdapter
 ViewModel      →  PlantViewModel (StateFlow + Coroutines)
 Repository     →  PlantRepository (Room + TFLite)
 ML Layer       →  PlantDiseaseClassifier, DiseaseInfoProvider
 Data Layer     →  Room DB (ScanResult, ScanResultDao)
```

---

## 🛠️ Tech Stack

- **Language:** Kotlin
- **Architecture:** MVVM + Repository
- **ML:** TensorFlow Lite 2.14.0 (on-device, offline inference)
- **Camera:** CameraX 1.3.1
- **Database:** Room 2.6.1 with Kotlin Flow
- **Async:** Kotlin Coroutines + StateFlow
- **Image Loading:** Glide 4.16.0
- **UI:** RecyclerView, ConstraintLayout, Material Components

---

## ⚠️ IMPORTANT — Model File Setup Required

This repo does **not** include the `.tflite` model binary (model files are large and shouldn't be committed directly without Git LFS).

**Before running the app, you must:**

1. Get a plant disease classification `.tflite` model. Easiest options:
   - **Teachable Machine** (no code, fastest): [teachablemachine.withgoogle.com](https://teachablemachine.withgoogle.com/train/image) — train on the [PlantVillage dataset](https://www.kaggle.com/datasets/emmarex/plantdisease) from Kaggle, then export as TensorFlow Lite.
   - **Kaggle**: search for pre-converted PlantVillage `.tflite` models.
   - **TensorFlow Hub**: search "plant disease classification".

2. Rename your downloaded model to **`plant_disease_model.tflite`**

3. Place it in:
   ```
   app/src/main/assets/plant_disease_model.tflite
   ```

4. If your model's classes differ from the default 19 PlantVillage classes, update:
   - `app/src/main/assets/labels.txt` — must match your model's output order exactly
   - `ml/DiseaseInfoProvider.kt` — update the keys to match your new labels

Full instructions are also in `app/src/main/assets/MODEL_SETUP_INSTRUCTIONS.txt`.

---

## 🚀 Setup & Run

### Step 1 — Clone the repo
```bash
git clone https://github.com/AdityaKaradbhajne/plant-disease-detector.git
cd plant-disease-detector
```

### Step 2 — Add your TFLite model
Follow the **Model File Setup** section above before building.

### Step 3 — Build and run
Open in Android Studio and click **Run**, or:
```bash
./gradlew assembleDebug
```
Minimum SDK: **24 (Android 7.0)**

> The app requests Camera permission on first launch.

---

## 📁 Project Structure

```
app/src/main/
├── java/com/example/plantdetector/
│   ├── data/
│   │   ├── ScanResult.kt          ← Room entity
│   │   ├── ScanResultDao.kt       ← Room DAO queries
│   │   ├── PlantDatabase.kt       ← Singleton Room DB
│   │   └── PlantRepository.kt    ← Bridges Room + TFLite classifier
│   ├── ml/
│   │   ├── PlantDiseaseClassifier.kt  ← Core TFLite inference engine
│   │   └── DiseaseInfoProvider.kt     ← Label → care tips mapping
│   ├── utils/
│   │   └── BitmapUtils.kt         ← EXIF-aware image loading
│   └── ui/
│       ├── PlantViewModel.kt      ← Core logic (classify, save, history)
│       ├── camera/MainActivity.kt ← CameraX preview + capture + gallery
│       ├── result/ResultActivity.kt
│       └── history/
│           ├── HistoryActivity.kt
│           └── ScanHistoryAdapter.kt
├── res/
│   ├── layout/                    ← All XML layouts
│   ├── drawable/                  ← Vector icons + shape drawables
│   └── values/                    ← Colors, strings, themes
└── assets/
    ├── plant_disease_model.tflite ← ⚠️ You must add this (see above)
    ├── labels.txt                 ← Model output class names
    └── MODEL_SETUP_INSTRUCTIONS.txt
```

---

## 💡 Key Implementation Details

**Offline-first** — All inference runs on-device via TensorFlow Lite. No internet connection is required to classify a plant photo.

**EXIF-aware image loading** — `BitmapUtils.kt` reads EXIF orientation metadata so photos captured in portrait mode aren't classified sideways.

**Background inference** — Classification runs on `Dispatchers.Default` since TFLite inference can block the main thread for 200-800ms.

**Confidence-based UI** — Results below a sensible confidence threshold can be flagged as "Unknown" via `DiseaseInfoProvider`'s fallback entry, encouraging users to retake a clearer photo.

---

## 📦 Key Dependencies

```kotlin
// TensorFlow Lite
implementation("org.tensorflow:tensorflow-lite:2.14.0")
implementation("org.tensorflow:tensorflow-lite-support:0.4.4")

// CameraX
implementation("androidx.camera:camera-camera2:1.3.1")
implementation("androidx.camera:camera-view:1.3.1")

// Room Database
implementation("androidx.room:room-runtime:2.6.1")
implementation("androidx.room:room-ktx:2.6.1")
kapt("androidx.room:room-compiler:2.6.1")
```

---

## 👨‍💻 Author

Built by **Aditya Karadbhajne** during the Android Development Internship at **Codec Technologies**
