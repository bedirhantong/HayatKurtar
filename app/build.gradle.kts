plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.appvalence.hayatkurtar"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.appvalence.hayatkurtar"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    androidTestImplementation(platform("androidx.compose:compose-bom:$composeBom"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")

    implementation("androidx.navigation:navigation-compose:$navigationComposeVersion")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")

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

    // Test
    testImplementation("junit:junit:${project.findProperty("junitVersion")}")
    androidTestImplementation("androidx.test.ext:junit:${project.findProperty("androidxTestExtJunitVersion")}")
    androidTestImplementation("androidx.test.espresso:espresso-core:${project.findProperty("espressoCoreVersion")}")
}

kapt {
    correctErrorTypes = true
}