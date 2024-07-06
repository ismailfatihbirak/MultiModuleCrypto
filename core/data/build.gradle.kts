plugins {
    alias(libs.plugins.multimodulecrypto.android.library)
    alias(libs.plugins.multimodulecrypto.android.hilt)
}

android {
    namespace = "com.example.multimodulecrypto.core.data"
}

dependencies {
    implementation(projects.core.model)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.play.services.auth)
    implementation(libs.firebase.firestore)
}