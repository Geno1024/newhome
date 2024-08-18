import g.buildsrc.BuildCount
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
}

dependencies {
    implementation(project(":ufi"))
}

val run = BuildCount(project, "run")

val runCount = tasks.register("runCount") {
    group = "buildCount"
    doLast {
        run.inc()
    }
}

tasks.withType<KotlinCompile> {
    dependsOn(runCount)
}
