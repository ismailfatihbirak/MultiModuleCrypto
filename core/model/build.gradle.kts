import com.example.multimodulecrypto.build_logic.convention.implementation

plugins {
    alias(libs.plugins.multimodulecrypto.android.library)
}

android {
    namespace = "com.example.multimodulecrypto.core.model"
}

dependencies {
    implementation(libs.gson)
}