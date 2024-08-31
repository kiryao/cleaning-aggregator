pluginManagement {
    val kotlinVersion: String by settings
    plugins {
        kotlin("jvm") version kotlinVersion apply false
    }
}

rootProject.name = "cleaning-aggregator"

include("m1l1-first")
