plugins {
    alias(libs.plugins.multimodulecrypto.android.library)
    alias(libs.plugins.multimodulecrypto.android.library.compose)
}

android {
    namespace = "com.example.multimodulecrypto.design_system"
}

dependencies {
    implementation(projects.core.common)
}