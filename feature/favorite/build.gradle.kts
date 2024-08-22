import com.example.multimodulecrypto.build_logic.convention.implementation

plugins {
    alias(libs.plugins.multimodulecrypto.android.feature)
    alias(libs.plugins.multimodulecrypto.android.library.compose)
}

android {
    namespace = "com.example.multimodulecrypto.favorite"
}

dependencies {
}