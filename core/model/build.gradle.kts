plugins {
    alias(libs.plugins.multimodulecrypto.android.library)
    
}

android {
    namespace = "com.example.multimodulecrypto.core.model"
}

dependencies {
    implementation(libs.gson)
}