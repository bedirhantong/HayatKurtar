plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.appvalence.hayatkurtar.testing"
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
    
    // Android & Kotlin
    implementation("androidx.core:core-ktx:${project.findProperty("coreKtxVersion")}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${project.findProperty("coroutinesVersion")}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${project.findProperty("coroutinesVersion")}")
    
    // Hilt
    implementation("com.google.dagger:hilt-android:${project.findProperty("hiltVersion")}")
    kapt("com.google.dagger:hilt-compiler:${project.findProperty("hiltVersion")}")
    
    // Testing utilities
    implementation("junit:junit:${project.findProperty("junitVersion")}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${project.findProperty("coroutinesVersion")}")
    implementation("io.mockk:mockk:1.13.8")
}

kapt {
    correctErrorTypes = true
}