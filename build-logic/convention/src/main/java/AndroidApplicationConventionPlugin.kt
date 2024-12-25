import com.android.build.api.dsl.ApplicationExtension
import com.example.multimodulecrypto.build_logic.convention.configureKotlinAndroid
import com.example.multimodulecrypto.build_logic.convention.implementation
import com.example.multimodulecrypto.build_logic.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidApplicationConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = 34

                packaging {
                    resources.excludes.addAll(
                        listOf(
                            "META-INF/LICENSE.md",
                            "META-INF/LICENSE-notice.md",
                        )
                    )
                }
            }
            dependencies {
                implementation(libs.findLibrary("firebase-analytics").get())
                implementation(libs.findLibrary("firebase-crashlytics").get())
            }
        }
    }
}
