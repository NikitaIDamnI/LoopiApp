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

rootProject.name = "Loopi"
include(":app")
include(":core:pexelsApi")
include(":core:data")
include(":core:database")
include(":core:firebaseServises")
include(":core:domain")
include(":core:di")
include(":features:authScreen")
include(":uikit")
include(":features:homeScreen")
include(":features:navigation")
include(":core:common")
include(":core:media")
include(":features:contentDetailsScreen")
