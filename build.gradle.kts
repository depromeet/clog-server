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

        // 기본
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.springframework.boot:spring-boot-starter")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

        // 웹 및 데이터
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")

        // Database
        implementation("com.h2database:h2")
        runtimeOnly("com.h2database:h2")

        // Spring Security 및 OAuth2
        implementation("org.springframework.boot:spring-boot-starter-security")
        implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
        implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
        implementation("com.auth0:java-jwt:4.4.0")
        implementation("com.auth0:jwks-rsa:0.20.0")

        // Swagger
        implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")

        // 유효성 검증
        implementation("org.hibernate.validator:hibernate-validator:8.0.1.Final")
        implementation("jakarta.validation:jakarta.validation-api:3.0.2")

        // 테스트
        testImplementation(kotlin("test"))
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")

        // 코드 품질 도구
        detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.23.7")
        kover(project(project.path))
    }

    detekt {
        config.setFrom(files("$rootDir/config/detekt.yml"))
        autoCorrect = true
        buildUponDefaultConfig = true
        debug = true
        tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
            reports {
                html.required.set(true)
                sarif.required.set(true)
            }
        }
    }

    kover {
        reports {
            total {
                xml { onCheck = true }
                html { onCheck = true }
            }
        }
    }

    java.sourceCompatibility = javaVersion
    java.targetCompatibility = javaVersion

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        compilerOptions {
            freeCompilerArgs.set(listOf("-Xjsr305=strict"))
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.fromTarget(javaVersion.toString()))
        }
    }

    tasks.test {
        useJUnitPlatform()
    }

    tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
        finalizedBy(reportMerge)
    }

    reportMerge {
        input.from(tasks.detekt.map { it.xmlReportFile })
    }
}
