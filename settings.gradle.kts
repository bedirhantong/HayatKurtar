pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "HayatKurtar"
include(":app")
include(":presentation")
include(":domain")
include(":data:mesh")
include(":data:transport:bluetooth")
include(":data:transport:wifidirect")
include(":core")
include(":di")
include(":testing")
include(":bluetooth") // Legacy module - will be refactored
