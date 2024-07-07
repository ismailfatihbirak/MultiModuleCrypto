import com.example.multimodulecrypto.build_logic.convention.implementation

plugins {
    alias(libs.plugins.multimodulecrypto.android.application)
    alias(libs.plugins.multimodulecrypto.android.application.compose)
    alias(libs.plugins.multimodulecrypto.android.hilt)
    alias(libs.plugins.google.services.plugin)
}

android {
    namespace = "com.example.multimodulecrypto"

    defaultConfig {
        applicationId = "com.example.multimodulecrypto"
        versionCode = 1
        versionName = "0.0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
        }
    }

}

dependencies {
    implementation(projects.feature.login)
    implementation(projects.feature.home)
    implementation(projects.feature.welcome)
    implementation(projects.feature.signup)
    implementation(projects.feature.detail)
    implementation(projects.feature.favorite)
    implementation(projects.core.common)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.androidx.navigation.compose)
}
