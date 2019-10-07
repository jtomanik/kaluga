@file:Suppress("UNCHECKED_CAST")

apply(from = "../gradle/publishable_component.gradle")

plugins {
    kotlin("multiplatform")
    id("jacoco")
    id("maven-publish")
    id("com.android.library")
}

val ext =  (gradle as ExtensionAware).extra


dependencies {
    implementation("com.google.android.gms:play-services-location:17.0.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.3.1")
}

kotlin {
    sourceSets {
        getByName("commonMain") {
            dependencies {
                implementation(project(":logging", ""))
            }
        }
    }
}

(ext["ios_suffix_archs"] as List<String>).forEach {
    kotlin {
        sourceSets {
            getByName("ios${it}Main") {
                dependencies {
                    implementation(project(":logging", "ios${it}Default"))
                }
            }
        }
    }
}