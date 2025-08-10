plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.kapt")
    id("com.google.dagger.hilt.android")
    id("maven-publish")
}

android {
    namespace = "com.appvalence.bluetooth"
    compileSdk = 35

    defaultConfig {
        minSdk = 23
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

    // Publish only the release variant + include sources
    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }
}

group = "com.appvalence"
version = "0.1.0"

dependencies {
    val coroutinesVersion = project.findProperty("coroutinesVersion") as String
    val hiltVersion = project.findProperty("hiltVersion") as String

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")

    implementation("com.google.dagger:hilt-android:$hiltVersion")
    kapt("com.google.dagger:hilt-compiler:$hiltVersion")

    implementation("androidx.core:core-ktx:${project.findProperty("coreKtxVersion")}")
}

kapt {
    correctErrorTypes = true
}

publishing {
    publications {
        create<MavenPublication>("mavenRelease") {
            // Ensure the 'release' component exists before wiring
            afterEvaluate {
                from(components["release"])
            }
            groupId = project.group.toString()
            artifactId = "bluetooth"
            version = project.version.toString()

            pom {
                name.set("HayatKurtar Bluetooth")
                description.set("Reusable Android Bluetooth (Classic + BLE) module with Hilt DI and coroutines.")
                url.set("https://github.com/appvalence/HayatKurtar")
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }
                developers {
                    developer {
                        id.set("appvalence")
                        name.set("AppValence")
                    }
                }
                scm {
                    url.set("https://github.com/appvalence/HayatKurtar")
                    connection.set("scm:git:https://github.com/appvalence/HayatKurtar.git")
                    developerConnection.set("scm:git:ssh://git@github.com/appvalence/HayatKurtar.git")
                }
            }
        }
    }

    repositories {
        // Always available for local testing
        mavenLocal()

        // Optional: publish to OSSRH if credentials are provided
        val ossrhUser: String? = (findProperty("ossrhUsername") as String?) ?: System.getenv("OSSRH_USERNAME")
        val ossrhPass: String? = (findProperty("ossrhPassword") as String?) ?: System.getenv("OSSRH_PASSWORD")
        if (!ossrhUser.isNullOrBlank() && !ossrhPass.isNullOrBlank()) {
            maven {
                name = "OSSRH"
                val isSnapshot = project.version.toString().endsWith("SNAPSHOT")
                url = uri(
                    if (isSnapshot)
                        "https://s01.oss.sonatype.org/content/repositories/snapshots/"
                    else
                        "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"
                )
                credentials {
                    username = ossrhUser
                    password = ossrhPass
                }
            }
        }
    }
}


