plugins {
    alias(libs.plugins.multimodulecrypto.android.library)
    alias(libs.plugins.multimodulecrypto.android.hilt)
}

android {
    namespace = "com.example.notifaction"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.model)
    implementation(projects.core.common)

    implementation(libs.workmanager)
    implementation(libs.androidx.navigation.compose)
}