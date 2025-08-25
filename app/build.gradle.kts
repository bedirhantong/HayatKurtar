plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.appvalence.hayatkurtar"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.appvalence.hayatkurtar"
        minSdk = 24 // Updated to API 24 for mesh networking features
        targetSdk = 35
        versionCode = 1
        versionName = "1.0.0"
        
        // App metadata for Play Store
        resValue("string", "app_version", versionName!!)
        resValue("string", "app_build", versionCode.toString())

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        
        // Vector drawable support for older devices
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    
    signingConfigs {
        create("release") {
            // These should be set via environment variables or gradle.properties for security
            // keyAlias = project.findProperty("RELEASE_KEY_ALIAS") as String? ?: "hayatkurtar"
            // keyPassword = project.findProperty("RELEASE_KEY_PASSWORD") as String? ?: ""
            // storeFile = file(project.findProperty("RELEASE_STORE_FILE") as String? ?: "keystore.jks")
            // storePassword = project.findProperty("RELEASE_STORE_PASSWORD") as String? ?: ""
            
            // For now, use debug keystore - REPLACE THIS FOR PRODUCTION
            keyAlias = "androiddebugkey"
            keyPassword = "android"
            storeFile = file("debug.keystore")
            storePassword = "android"
        }
    }

    buildTypes {
        debug {
            isDebuggable = true
            isMinifyEnabled = false
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
            
            // Debugging manifest placeholders
            manifestPlaceholders["appName"] = "HayatKurtar Debug"
            manifestPlaceholders["appIcon"] = "@mipmap/ic_launcher"
        }
        
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
            
            signingConfig = signingConfigs.getByName("release")
            
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            
            // Production manifest placeholders
            manifestPlaceholders["appName"] = "HayatKurtar"
            manifestPlaceholders["appIcon"] = "@mipmap/ic_launcher"
            
            // Bundle configuration for Play Store
            bundle {
                language {
                    enableSplit = true
                }
                density {
                    enableSplit = true
                }
                abi {
                    enableSplit = true
                }
            }
        }
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
        kotlinCompilerExtensionVersion = project.findProperty("composeCompiler") as String
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    val lifecycleVersion = project.findProperty("lifecycleVersion") as String
    val activityComposeVersion = project.findProperty("activityComposeVersion") as String
    val navigationComposeVersion = project.findProperty("navigationComposeVersion") as String
    val roomVersion = project.findProperty("roomVersion") as String
    val coroutinesVersion = project.findProperty("coroutinesVersion") as String
    val hiltVersion = project.findProperty("hiltVersion") as String
    val coreKtxVersion = project.findProperty("coreKtxVersion") as String
    val composeBom = project.findProperty("composeBom") as String
    val securityCryptoVersion = project.findProperty("securityCryptoVersion") as String
    val tinkAndroidVersion = project.findProperty("tinkAndroidVersion") as String

    implementation("androidx.core:core-ktx:$coreKtxVersion")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    implementation("androidx.activity:activity-compose:$activityComposeVersion")

    implementation(platform("androidx.compose:compose-bom:$composeBom"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material-icons-extended")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    androidTestImplementation(platform("androidx.compose:compose-bom:$composeBom"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")

    implementation("androidx.navigation:navigation-compose:$navigationComposeVersion")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")
    
    // SplashScreen API (Android 12+ with backwards compatibility)
    implementation("androidx.core:core-splashscreen:1.0.1")

    // Hilt
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    kapt("com.google.dagger:hilt-compiler:$hiltVersion")

    // Room
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")

    // DataStore Preferences
    implementation("androidx.datastore:datastore-preferences:1.1.1")

    // Crypto & Keystore helpers
    implementation("androidx.security:security-crypto:$securityCryptoVersion")
    implementation("com.google.crypto.tink:tink-android:$tinkAndroidVersion")

    // Module dependencies
    implementation(project(":core"))
    implementation(project(":domain"))
    implementation(project(":data:mesh"))
    implementation(project(":data:transport:bluetooth"))
    implementation(project(":data:transport:wifidirect"))
    implementation(project(":presentation"))
    implementation(project(":di"))
    // Removed legacy :bluetooth module to avoid conflicts

    // Test
    testImplementation("junit:junit:${project.findProperty("junitVersion")}")
    androidTestImplementation("androidx.test.ext:junit:${project.findProperty("androidxTestExtJunitVersion")}")
    androidTestImplementation("androidx.test.espresso:espresso-core:${project.findProperty("espressoCoreVersion")}")
}

kapt {
    correctErrorTypes = true
}