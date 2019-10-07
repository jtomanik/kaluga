
allprojects {
    repositories {
        mavenLocal()
        mavenCentral()
        google()
        jcenter()
        maven("https://dl.bintray.com/pocketbyte/hydra/")

// uncomment for early access / dev releases
//        maven { url 'https://dl.bintray.com/kotlin/kotlin-dev' }
//        maven { url 'https://dl.bintray.com/kotlin/kotlin-eap' }

    }
}
