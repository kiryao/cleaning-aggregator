plugins {
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependencies)
    alias(libs.plugins.spring.kotlin)
    alias(libs.plugins.kotlinx.serialization)
    id("build-jvm")
}

dependencies {
    implementation(libs.spring.actuator)
    implementation(libs.spring.webflux)
    implementation(libs.spring.webflux.ui)
    implementation(kotlin("reflect"))
    implementation(kotlin("stdlib"))

    implementation(libs.coroutines.core)
    implementation(libs.coroutines.reactor)
    implementation(libs.coroutines.reactive)
    implementation(libs.kotlinx.serialization.core)
    implementation(libs.kotlinx.serialization.json)

    // internal models
    implementation(project(":common"))
    implementation(project(":app-common"))
    implementation("ru.otus.otuskotlin.cleaningaggregator.libs:lib-logging-logback")

    // api
    implementation(project(":api-v1-kmp"))

    // biz
    implementation(project(":biz"))

    // tests
    testImplementation(kotlin("test-junit5"))
    testImplementation(libs.spring.test)
    testImplementation(libs.mockito.kotlin)

    // stubs
    testImplementation(project(":stubs"))
}

tasks {
    withType<ProcessResources> {
        val file = rootProject.ext["spec-v1"] as String

        from(file) {
            into("/static")
            filter {
                // Устанавливаем версию в сваггере
                it.replace("\${VERSION_APP}", project.version.toString())
            }
        }
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
