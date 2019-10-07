kotlin {

}

apply(from="../gradle/publishable_component.gradle")

//kotlin {
//    sourceSets {
//        getByName("commonMain") {
//            dependencies {
//                implementation(project(":logging", ""))
//            }
//        }
//    }
//}
//    sourceSets {
//        getByName("commonTest") {
//            dependencies {
//                implementation(project(":test", ""))
//            }
//        }
//    }
//}

val ext =  (gradle as ExtensionAware).extra
(ext["ios_suffix_archs"] as List<String>).forEach {
//    kotlin {
        sourceSets {
            getByName("ios${it}Main") {
                dependencies {
                    implementation(project(":logging", "ios${it}Default"))
                }
            }
        }
//    }
}