import com.android.build.gradle.tasks.PackageAndroidArtifact
import g.buildsrc.BuildCount

plugins {
    id("com.android.application") version "8.5.2"
    kotlin("android") version "1.9.23"
}

kotlin {
    jvmToolchain(21)
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation("androidx.drawerlayout", "drawerlayout", "1.2.0")
    implementation("androidx.navigation", "navigation-ui-ktx", "2.7.7")
    implementation("androidx.navigation", "navigation-fragment-ktx", "2.7.7")

    implementation(project(":ufi"))
}

val run = BuildCount(project, "run")

val runCount = tasks.register("runCount") {
    group = "buildCount"
    doLast {
        run.inc()
    }
}

val android = BuildCount(project, "android")

val androidCount = tasks.register("androidCount") {
    group = "buildCount"
    doLast {
        android.inc()
    }
}

tasks.withType<JavaCompile> {
    dependsOn(runCount)
}

tasks.withType<PackageAndroidArtifact> {
    dependsOn(androidCount)
}

android {
    compileSdk = 35
    namespace = "g.sw.planet"
    defaultConfig {
        applicationId = "g.sw.planet"
        minSdk = 17
        targetSdk = 35
        versionCode = android.read() + 1
        versionName = "0.0.1.$versionCode"
    }
}
