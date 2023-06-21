import kr.sjh.buildsrc.Libraries
import kr.sjh.buildsrc.implementation

plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_7
    targetCompatibility = JavaVersion.VERSION_1_7
}

dependencies {

    //corutine
    implementation(Libraries.corutineLibraries)

    implementation(Libraries.JavaX.INJECT)

    implementation("joda-time:joda-time:2.12.5")

}