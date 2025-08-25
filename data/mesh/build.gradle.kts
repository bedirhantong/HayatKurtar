plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.appvalence.hayatkurtar.data.mesh"
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
    
    // Room
    implementation("androidx.room:room-runtime:${project.findProperty("roomVersion")}")
    implementation("androidx.room:room-ktx:${project.findProperty("roomVersion")}")
    kapt("androidx.room:room-compiler:${project.findProperty("roomVersion")}")
    
    // Collections & Utilities
    implementation("androidx.collection:collection-ktx:1.3.0")
    
    // Testing
    testImplementation("junit:junit:${project.findProperty("junitVersion")}")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${project.findProperty("coroutinesVersion")}")
    testImplementation("io.mockk:mockk:1.13.8")
    testImplementation("androidx.room:room-testing:${project.findProperty("roomVersion")}")
}

kapt {
    correctErrorTypes = true
}