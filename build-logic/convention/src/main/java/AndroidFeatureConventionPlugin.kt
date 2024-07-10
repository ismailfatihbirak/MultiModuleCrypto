import com.example.multimodulecrypto.build_logic.convention.implementation
import com.example.multimodulecrypto.build_logic.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("multimodulecrypto.android.library")
                apply("multimodulecrypto.android.hilt")
                apply("org.jetbrains.kotlin.plugin.serialization")
            }

            dependencies {
                implementation(project(":core:model"))
                implementation(project(":core:domain"))
                implementation(project(":core:common"))
                implementation(project(":core:design-system"))

                // Define common dependencies for feature modules
                implementation(libs.findLibrary("androidx-navigation-compose").get())
                implementation(libs.findLibrary("kotlinx-serialization-json").get())
                implementation(libs.findLibrary("hilt_navigation_compose").get())
                implementation(libs.findLibrary("coil").get())
            }
        }
    }
}
