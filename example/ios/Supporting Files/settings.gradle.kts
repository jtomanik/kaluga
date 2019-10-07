import java.util.*

pluginManagement {
    resolutionStrategy {
        eachPlugin {


            //val properties = Properties(); properties.load(File("../../../gradle.properties").inputStream())
            // above works for build, but it causes problems in the IDE at the moment. Keep up to date with gradle.properties in root of kaluga
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
 * Changes made to this file should also be reflected in the `settings.gradle` in the root of the project
 *
 ***********************************************/


apply("../../../gradle/ext.gradle")

if (!((gradle as ExtensionAware).extra["exampleAsRoot"] as Boolean)) {
    include(":Components")
    project(":Components").projectDir = file("../../../Components")

    include(":logging")
    project(":logging").projectDir = file("../../../logging")

}

include (":android")
project(":android").projectDir = file("../../android")

include(":KotlinNativeFramework")
project(":KotlinNativeFramework").projectDir = file("../KotlinNativeFramework")

include(":shared")
project(":shared").projectDir = file("../../shared")

rootProject.name = file("..").name
