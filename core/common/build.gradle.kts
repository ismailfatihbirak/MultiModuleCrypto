import com.example.multimodulecrypto.build_logic.convention.implementation

plugins {
    alias(libs.plugins.multimodulecrypto.android.library)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.example.multimodulecrypto.core.common"
}

dependencies {
    implementation(libs.kotlinx.serialization.json)
}