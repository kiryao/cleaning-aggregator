plugins {
    id("build-kmp")
}

kotlin {
    sourceSets {
        val coroutinesVersion: String by project
        commonMain {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
                implementation(libs.coroutines.core)

                // transport models
                implementation(project(":common"))
                implementation(project(":api-log"))

                implementation(project(":biz"))
            }
        }
        commonTest {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))

                implementation(libs.coroutines.core)
                implementation(libs.coroutines.test)

                implementation(project(":api-v1-kmp"))
            }
        }

        jvmTest {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        nativeTest {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}
