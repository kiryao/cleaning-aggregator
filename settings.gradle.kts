plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "cleaning-aggregator"

includeBuild("backend")
includeBuild("libs")
