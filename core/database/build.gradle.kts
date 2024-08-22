plugins {
    alias(libs.plugins.multimodulecrypto.android.library)
    alias(libs.plugins.multimodulecrypto.android.room)
    alias(libs.plugins.multimodulecrypto.android.hilt)
}

android {
    namespace = "com.example.multimodulecrypto.database"
}

dependencies {
}