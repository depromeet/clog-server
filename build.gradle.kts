import io.gitlab.arturbosch.detekt.Detekt

plugins {
    id("org.springframework.boot") version "3.4.2"
    id("io.spring.dependency-management") version "1.1.6"

    kotlin("jvm") version "2.0.10"
    kotlin("plugin.spring") version "2.0.10"
    kotlin("plugin.jpa") version "2.0.10"

    id("io.gitlab.arturbosch.detekt") version "1.23.7"
    id("org.jetbrains.kotlinx.kover") version "0.9.1"
}

val javaVersion = JavaVersion.VERSION_21
java.sourceCompatibility = javaVersion
java.targetCompatibility = javaVersion

allprojects {
    group = "org.depromeet"
    version = "1.0-SNAPSHOT"

    repositories {
        mavenCentral()
        maven(url = "https://oss.sonatype.org/content/repositories/snapshots")
    }
}

val reportMerge by tasks.registering(io.gitlab.arturbosch.detekt.report.ReportMergeTask::class) {
    output.set(rootProject.layout.buildDirectory.file("reports/detekt/merge.xml"))
}

subprojects {
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "org.jetbrains.kotlin.plugin.jpa")
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "io.gitlab.arturbosch.detekt")
    apply(plugin = "org.jetbrains.kotlinx.kover")

    dependencies {
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.springframework.boot:spring-boot-starter")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

        testImplementation(kotlin("test"))
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")

        detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.23.7")
        kover(project(project.path))
    }

    detekt {
        config.setFrom(
            files("$rootDir/config/detekt.yml")
        )
        autoCorrect = true
        buildUponDefaultConfig = true
        debug = true
        tasks.withType<Detekt>().configureEach {
            reports {
                html.required.set(true)
                sarif.required.set(true)
            }
        }
    }

    kover {
        reports {
            total {
                xml {
                    onCheck = true
                }
                html {
                    onCheck = true
                }
            }
        }
    }

    java.sourceCompatibility = javaVersion
    java.targetCompatibility = javaVersion

    tasks {
        compileKotlin {
            kotlinOptions {
                freeCompilerArgs = listOf("-Xjsr305=strict")
                jvmTarget = javaVersion.toString()
            }
        }

        test {
            useJUnitPlatform()
        }

        detekt {
            configureEach {
                finalizedBy(reportMerge)
            }
        }
    }

    reportMerge {
        input.from(tasks.detekt.map { it.xmlReportFile })
    }
}