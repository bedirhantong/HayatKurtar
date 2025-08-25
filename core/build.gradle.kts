plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.appvalence.hayatkurtar.core"
    compileSdk = 35

    defaultConfig {
        minSdk = 24  // Updated for better compatibility
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
    // Kotlin & Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${project.findProperty("coroutinesVersion")}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${project.findProperty("coroutinesVersion")}")
    
    // Hilt
    implementation("com.google.dagger:hilt-android:${project.findProperty("hiltVersion")}")
    kapt("com.google.dagger:hilt-compiler:${project.findProperty("hiltVersion")}")
    
    // Crypto
    implementation("com.google.crypto.tink:tink-android:${project.findProperty("tinkAndroidVersion")}")
    implementation("androidx.security:security-crypto:${project.findProperty("securityCryptoVersion")}")
    
    // Android Core
    implementation("androidx.core:core-ktx:${project.findProperty("coreKtxVersion")}")
    
    // Logging
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.2")
    
    // Testing
    testImplementation("junit:junit:${project.findProperty("junitVersion")}")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${project.findProperty("coroutinesVersion")}")
    testImplementation("io.mockk:mockk:1.13.8")
}

kapt {
    correctErrorTypes = true
}