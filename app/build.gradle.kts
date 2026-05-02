// WHAT:
// Core build config for the app module.
// Declares applicationId, SDK versions, build types, Java/Kotlin targets, the Compose feature flag,
// and all dependencies.
//
// NEEDED: Yes — required.
// This is how the Android Gradle Plugin knows how to compile and package your app.
//
// MAINTAINANCE:
// Update compileSdk/targetSdk yearly.
// Add new dependencies in the `dependencies {}` block and their versions in `libs.versions.toml`.

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "bew.helloworld"
    compileSdk = 35

    defaultConfig {
        applicationId = "bew.helloworld"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        compilerOptions {
            jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17
        }
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    // note: names in `[libraries]` section in `libs.versions.toml` are accessible directly in
    // `libs.*`. (e.g. `foo-bar-baz = ...` in the toml file become `libs.foo.bar.baz` here)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.material3)
    implementation(libs.google.material)
    debugImplementation(libs.androidx.ui.tooling)
    implementation(libs.timber)
}
