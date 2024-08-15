plugins {
    alias(libs.plugins.multimodulecrypto.android.library)
    alias(libs.plugins.multimodulecrypto.android.room)
}

android {
    namespace = "com.example.database"
}

dependencies {
    implementation(projects.core.model)
}