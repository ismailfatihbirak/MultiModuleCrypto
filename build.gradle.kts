plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.google.services.plugin) apply false
}
//buildscript {
//    repositories {
//        mavenCentral()
//    }
//    dependencies {
//        classpath("com.vanniktech:gradle-dependency-graph-generator-plugin:0.8.0")
//    }
//}
//
//apply(plugin = "com.vanniktech.dependency.graph.generator")
