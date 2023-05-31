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
        const val LIFECYCLE_LIVEDATA =
            "androidx.lifecycle:lifecycle-livedata:${Versions.LIFECYCLE_VERSION}"
        const val LIFECYCLE_VIEWMODEL =
            "androidx.lifecycle:lifecycle-viewmodel:${Versions.LIFECYCLE_VERSION}"

        const val COROUTINE =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.COROUTINE}"

        const val ROOM = "androidx.room:room-runtime:${Versions.ROOM}"
        const val ROOM_KAPT = "androidx.room:room-compiler:${Versions.ROOM}"
    }

    val uiLibraries = arrayListOf<String>().apply {
        add(AndroidX.APP_COMPAT)
        add(AndroidX.MATERIAL)
        add(AndroidX.CONSTRAINT_LAYOUT)
    }

    val ktxLibraries = arrayListOf<String>().apply {
        add(KTX.CORE)
    }

    val navigationLibraries = arrayListOf<String>().apply {
        add(AndroidX.ACTIVITY_KTX)
        add(AndroidX.FRAGMENT_KTX)
    }

    val jetPackLibraries = arrayListOf<String>().apply {
        add(AndroidX.LIFECYCLE_LIVEDATA)
        add(AndroidX.LIFECYCLE_VIEWMODEL)
    }

    val corutineLibraries = arrayListOf<String>().apply {
        add(AndroidX.COROUTINE)
    }

    val roomLibraries = arrayListOf<String>().apply {
        add(AndroidX.ROOM)
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


    const val CALENDAR =
        "com.kizitonwose.calendar:view:${Versions.CALENDAR}"

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

    //LiveData
    const val LIFECYCLE_VERSION = "2.5.1"

    //corutine
    const val COROUTINE = "1.3.9"

    //room
    const val ROOM = "2.5.1"

    //kizitonwose
    const val CALENDAR = "2.3.0"

}


//util functions for adding the different type dependencies from build.gradle file
fun DependencyHandler.kapt(list: List<String>) {
    list.forEach { dependency ->
        add("kapt", dependency)
    }
}

fun DependencyHandler.implementation(list: List<String>) {
    list.forEach { dependency ->
        add("implementation", dependency)
    }
}

fun DependencyHandler.androidTestImplementation(list: List<String>) {
    list.forEach { dependency ->
        add("androidTestImplementation", dependency)
    }
}

fun DependencyHandler.testImplementation(list: List<String>) {
    list.forEach { dependency ->
        add("testImplementation", dependency)
    }
}