plugins {
    alias(libs.plugins.multimodulecrypto.android.library)
    alias(libs.plugins.multimodulecrypto.android.hilt)
}

android {
    namespace = "com.example.multimodulecrypto.domain"
}

dependencies {
    implementation(projects.core.network)
    implementation(projects.core.common)
    implementation(projects.core.model)
    implementation(projects.core.data)
    implementation(projects.core.database)
}