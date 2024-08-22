plugins {
    alias(libs.plugins.multimodulecrypto.android.library)
    alias(libs.plugins.multimodulecrypto.android.hilt)
}

android {
    namespace = "com.example.multimodulecrypto.network"
}

dependencies {
    implementation(libs.retrofit)
    implementation(libs.gson)
    implementation(libs.converter.gson)
    implementation(projects.core.model)
}