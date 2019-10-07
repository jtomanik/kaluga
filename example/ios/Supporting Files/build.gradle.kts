buildscript {
    repositories {
        mavenCentral()
        google()
        jcenter()
    }

    dependencies {
        val ext = (gradle as ExtensionAware).extra
        classpath("com.android.tools.build:gradle:${ext["android_build_version"]}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${ext["kotlin_version"]}")
        classpath("com.google.gms:google-services:4.3.1")
    }
}
