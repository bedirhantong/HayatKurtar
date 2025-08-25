plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.appvalence.hayatkurtar.di"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    // Module dependencies
    implementation(project(":core"))
    implementation(project(":domain"))
    implementation(project(":data:mesh"))
    implementation(project(":data:transport:bluetooth"))
    implementation(project(":data:transport:wifidirect"))
    implementation(project(":bluetooth")) // Legacy module
    
    // Android & Kotlin
    implementation("androidx.core:core-ktx:${project.findProperty("coreKtxVersion")}")
    
    // Hilt
    implementation("com.google.dagger:hilt-android:${project.findProperty("hiltVersion")}")
    kapt("com.google.dagger:hilt-compiler:${project.findProperty("hiltVersion")}")
    
    // Room
    implementation("androidx.room:room-runtime:${project.findProperty("roomVersion")}")
    implementation("androidx.room:room-ktx:${project.findProperty("roomVersion")}")
}

kapt {
    correctErrorTypes = true
}