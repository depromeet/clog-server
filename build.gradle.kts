import io.gitlab.arturbosch.detekt.Detekt
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompilerOptions

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
        // ✅ 기본 의존성
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.springframework.boot:spring-boot-starter")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

        // ✅ DB 관련 (H2 추가)
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")
        implementation("com.h2database:h2") // H2 Database 추가
        runtimeOnly("com.h2database:h2") // 런타임 전용
        implementation("org.springframework.boot:spring-boot-starter-data-jpa") // ✅ JPA 추가
        implementation("org.springframework.boot:spring-boot-starter-web")
        // ✅ Spring Security & OAuth2
        implementation("org.springframework.boot:spring-boot-starter-security") // Spring Security
        implementation("org.springframework.boot:spring-boot-starter-oauth2-client") // OAuth2 클라이언트
        implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server") // OAuth2 리소스 서버 (JWT 지원)
        implementation("com.auth0:java-jwt:4.4.0")

        // ✅ Swagger (API 문서화)
        implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")

        // ✅ 테스트 관련
        testImplementation(kotlin("test"))
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")

        // ✅ 코드 품질 도구
        detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.23.7")
        kover(project(project.path))
    }

    // ✅ Detekt 코드 정적 분석 설정
    detekt {
        config.setFrom(files("$rootDir/config/detekt.yml"))
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

    // ✅ 코드 커버리지(Kover) 설정
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

    // ✅ Kotlin 컴파일러 설정 (Deprecated된 `kotlinOptions` -> `compilerOptions` 사용)
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        compilerOptions {
            freeCompilerArgs.set(listOf("-Xjsr305=strict"))
            jvmTarget.set(JvmTarget.fromTarget(javaVersion.toString()))
        }
    }

    // ✅ JUnit 설정
    tasks.test {
        useJUnitPlatform()
    }

    // ✅ Detekt 리포트 병합 설정
    tasks.withType<Detekt>().configureEach {
        finalizedBy(reportMerge)
    }

    reportMerge {
        input.from(tasks.detekt.map { it.xmlReportFile })
    }
}
