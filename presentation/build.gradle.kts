plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.appvalence.hayatkurtar.presentation"
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
    
    buildFeatures {
        compose = true
    }
    
    composeOptions {
        kotlinCompilerExtensionVersion = "${project.findProperty("composeCompiler")}"
    }
}

dependencies {
    // Module dependencies
    implementation(project(":core"))
    implementation(project(":domain"))
    implementation(project(":data:mesh"))
    
    // Android & Kotlin
    implementation("androidx.core:core-ktx:${project.findProperty("coreKtxVersion")}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${project.findProperty("coroutinesVersion")}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${project.findProperty("coroutinesVersion")}")
    
    // Compose BOM
    implementation(platform("androidx.compose:compose-bom:${project.findProperty("composeBom")}"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material-icons-extended")
    
    // Lifecycle & ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:${project.findProperty("lifecycleVersion")}")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:${project.findProperty("lifecycleVersion")}")
    
    // Hilt
    implementation("com.google.dagger:hilt-android:${project.findProperty("hiltVersion")}")
    kapt("com.google.dagger:hilt-compiler:${project.findProperty("hiltVersion")}")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")
    
    // Permissions
    implementation("com.google.accompanist:accompanist-permissions:0.32.0")
    
    // QR Code
    implementation("com.google.zxing:core:3.5.2")
    implementation("com.journeyapps:zxing-android-embedded:4.3.0")
    
    // Testing
    testImplementation("junit:junit:${project.findProperty("junitVersion")}")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}

kapt {
    correctErrorTypes = true
}