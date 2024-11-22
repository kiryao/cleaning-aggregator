plugins {
    id("build-jvm")
    application
}

application {
    mainClass.set("ru.otus.otuskotlin.cleaningaggregator.app.log.tmp.MainKt")
}

dependencies {
    implementation(project(":api-log"))
    implementation("ru.otus.otuskotlin.cleaningaggregator.libs:lib-logging-common")
    implementation("ru.otus.otuskotlin.cleaningaggregator.libs:lib-logging-logback")

    implementation(project(":common"))

    implementation(libs.coroutines.core)
}
