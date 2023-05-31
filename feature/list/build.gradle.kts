import kr.sjh.buildsrc.implementation
import kr.sjh.buildsrc.AppConfig
import kr.sjh.buildsrc.Libraries

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
}

android {
    namespace = "kr.sjh.list"
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(project(":domain"))

    // AndroidX
    implementation(Libraries.uiLibraries)

    // KTX
    implementation(Libraries.KTX.CORE)

    // TEST
    testImplementation(Libraries.Test.JUNIT)

    // AndroidTest
    androidTestImplementation(Libraries.AndroidTest.ESPRESSO_CORE)

    // di
    implementation(Libraries.Hilt.HILT_ANDROID)
    kapt(Libraries.Hilt.HILT_COMPILER)

    // jetpack(ViewModel, LiveData)
    implementation(Libraries.jetPackLibraries)

    // navigation
    implementation(Libraries.navigationLibraries)

    // horizontalcalendar
    implementation(Libraries.CALENDAR)
}