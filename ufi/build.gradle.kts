import g.buildsrc.BuildCount
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "2.0.0"
}

dependencies {
}

val run = BuildCount(project, "run")

val runCount = tasks.register("runCount") {
    group = "buildCount"
    doLast {
        run.inc()
    }
}

val jar = BuildCount(project, "jar")

val jarCount = tasks.register("jarCount") {
    group = "buildCount"
    doLast {
        jar.inc()
    }
}

tasks.withType<KotlinCompile> {
    dependsOn(runCount)
}

tasks.withType<Jar> {
    dependsOn(jarCount)
    from(configurations.runtimeClasspath.get().map {
        if (it.isFile) zipTree(it) else it
    })
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    manifest {
        attributes(
            "Created-By" to "Geno1024"
        )
    }
}
