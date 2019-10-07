import java.util.*

pluginManagement {
    resolutionStrategy {
        eachPlugin {

            //val properties = Properties(); properties.load(File("gradle.properties").inputStream())
            val properties = mapOf("kotlin_version" to "1.3.50", "android_build_version" to "3.6.0-alpha12")

            if (requested.id.id == "org.jetbrains.kotlin.multiplatform") {
                useModule("org.jetbrains.kotlin:kotlin-gradle-plugin:${requested.version ?: properties["kotlin_version"]}")
            }
            if (requested.id.id == "com.android.library") {
                useModule("com.android.tools.build:gradle:${requested.version ?: properties["android_build_version"]}")
            }
            if (requested.id.id == "com.android.application") {
                useModule("com.android.tools.build:gradle:${requested.version ?: properties["android_build_version"]}")
            }
        }
    }

    repositories {
        gradlePluginPortal()
        google()
        jcenter()
    }
}

/***********************************************
 *
 * Changes made to this file should also be reflected in the `settings.gradle` under [example/ios/Supporting Files]
 *
 ***********************************************/

rootProject.name = "Kaluga"

apply (from = "gradle/ext.gradle")

include(":logging")
include (":permissions")
include(":location")
include(":test")
include (":Components")