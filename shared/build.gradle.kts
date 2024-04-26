plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    kotlin("plugin.serialization") version "1.7.10"
    id("com.android.library")
    `maven-publish`
}

val ktorVersion = "2.0.2"

kotlin {
    android {
        publishLibraryVariants("debug", "release")
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    tvosX64()
    tvosArm64()
    tvosSimulatorArm64()

    cocoapods {
        summary = "Core Module"
        homepage = ""
        version = "1.0.0"
        ios.deploymentTarget = "14.0"
        framework {
            baseName = "XNCore"
            transitiveExport = true
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
                api("io.ktor:ktor-client-core:$ktorVersion")
                api("io.ktor:ktor-client-content-negotiation:$ktorVersion")
                api("io.ktor:ktor-client-logging:$ktorVersion")
                api("com.squareup.okio:okio:3.2.0")
                api("io.github.aakira:napier:2.6.1")
            }
        }
        val commonDebug by sourceSets.creating {
            dependsOn(commonMain)
        }
        val commonRelease by sourceSets.creating {
            dependsOn(commonMain)
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val tvosX64Main by getting
        val tvosArm64Main by getting
        val tvosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            tvosX64Main.dependsOn(this)
            tvosArm64Main.dependsOn(this)
            tvosSimulatorArm64Main.dependsOn(this)
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
    sourceSets["iosMain"].dependencies {
        implementation("io.ktor:ktor-client-ios:2.1.2")
    }
    sourceSets["androidMain"].dependencies {
        implementation("io.ktor:ktor-client-android:2.1.2")
        implementation("io.ktor:ktor-client-okhttp:$ktorVersion")
        implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
        implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
        implementation("io.ktor:ktor-client-logging:$ktorVersion")
        implementation("io.github.aakira:napier:2.6.1")
    }
}

android {
    namespace = "com.dschonfeld.kmp"
    compileSdk = 32
    defaultConfig {
        minSdk = 21
        targetSdk = 32
    }
}

kotlin {
    macosX64("native") {
        binaries {
            framework {
                baseName = "kmp"
            }
        }
    }
}

publishing {
    publications.withType(MavenPublication::class) {
        groupId = "com.dschonfeld"
        artifactId = "kmp"
        version = "1.0.0"

        //Build tasks

        //For Android: publishing -> publishToMavenLocal
        //output in maven local folder

        //For IOS: cocoapods -> podPublishXCFramework
        //output shared/build/cocoapods/publish/release

        pom {
            name.set("XNCore")
            description.set("Core Module")
            url.set("")

            organization {
                name.set("XN")
            }
        }
    }
}