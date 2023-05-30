package kr.sjh.buildsrc

import org.gradle.api.artifacts.dsl.DependencyHandler

object Libraries {

    object AndroidX {
        const val APP_COMPAT = "androidx.appcompat:appcompat:${Versions.APP_COMPAT}"
        const val MATERIAL = "com.google.android.material:material:${Versions.MATERIAL}"
        const val CONSTRAINT_LAYOUT =
            "androidx.constraintlayout:constraintlayout:${Versions.CONSTRAINT_LAYOUT}"
        const val ACTIVITY_KTX = "androidx.navigation:navigation-ui-ktx:${Versions.NAVIGATION}"
        const val FRAGMENT_KTX =
            "androidx.navigation:navigation-fragment-ktx:${Versions.NAVIGATION}"
        const val LIFECYCLE_LIVEDATA = ""
        const val LIFECYCLE_SCOPE = ""
    }

    object KTX {
        const val CORE = "androidx.core:core-ktx:${Versions.CORE}"
    }

    object Test {
        const val JUNIT = "androidx.test.ext:junit:${Versions.JUNIT}"
    }

    object AndroidTest {
        const val ESPRESSO_CORE = "androidx.test.espresso:espresso-core:${Versions.ESPRESSO_CORE}"
    }

    object Hilt {
        const val HILT_ANDROID = "com.google.dagger:hilt-android:${Versions.HILT_ANDROID}"
        const val HILT_COMPILER = "com.google.dagger:hilt-compiler:${Versions.HILT_COMPILER}"
    }


}

object Versions {
    // AndroidX
    const val APP_COMPAT = "1.4.1"
    const val MATERIAL = "1.5.0"
    const val CONSTRAINT_LAYOUT = "2.1.4"

    // KTX
    const val CORE = "1.7.0"

    // TEST
    const val JUNIT = "1.1.3"

    // Android Test
    const val ESPRESSO_CORE = "3.4.0"

    //Hilt-Android
    const val HILT_ANDROID = "2.44"

    //Hilt-compiler
    const val HILT_COMPILER = "2.44"

    //NAVIGATION
    const val NAVIGATION = "2.5.3"
}