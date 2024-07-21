// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    alias(libs.plugins.ktlint)
}

dependencies {
    ktlintRuleset(libs.ktlint.compose)
}

buildscript {
    dependencies {
        classpath("com.facebook.react:react-native-gradle-plugin")
    }
}
