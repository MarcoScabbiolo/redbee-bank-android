import groovy.lang.Closure

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
    // Disabling following line as RN does not support this yet
    // repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

apply(file("./node_modules/@react-native-community/cli-platform-android/native_modules.gradle"))
(extra.get("applyNativeModulesSettingsGradle") as Closure<*>)(settings)

rootProject.name = "redbee-bank"
includeBuild("./node_modules/@react-native/gradle-plugin")
include(":app")
