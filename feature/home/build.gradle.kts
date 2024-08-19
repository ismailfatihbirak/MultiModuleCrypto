plugins {
    alias(libs.plugins.multimodulecrypto.android.feature)
    alias(libs.plugins.multimodulecrypto.android.library.compose)
}

android {
    namespace = "com.example.multimodulecrypto.feature.home"

}

dependencies {
    implementation(projects.core.notifaction)
    implementation(projects.core.offlinecache)
}