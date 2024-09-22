pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.(android|google).*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

include(":ufi")
include(":swdb:planet:android")
include(":swdb:greqsndr")
include(":swdb:mgmts")
include(":swdb:reqdistr")
include(":swdb:svctpl")
//File("swdb").list()?.forEach { include(":swdb:$it".apply(::println)) }
include(":lang:l0")
