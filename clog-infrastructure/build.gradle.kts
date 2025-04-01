dependencies {
    implementation(project(":clog-global-utils"))
    implementation(project(":clog-domain"))

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("com.github.gavlyukovskiy:p6spy-spring-boot-starter:1.10.0")
    implementation("org.apache.httpcomponents.client5:httpclient5:5.4.2")

    implementation("com.linecorp.kotlin-jdsl:jpql-dsl:3.5.4")
    implementation("com.linecorp.kotlin-jdsl:jpql-render:3.5.4")

    implementation("com.amazonaws:aws-java-sdk-s3:1.12.632")

    implementation("com.linecorp.kotlin-jdsl:hibernate-support:3.5.4")
    implementation("com.linecorp.kotlin-jdsl:spring-data-jpa-support:3.5.4")

    implementation("org.hibernate:hibernate-spatial:6.6.11.Final")
    runtimeOnly("org.locationtech.jts:jts-core:1.20.0")
    runtimeOnly("com.mysql:mysql-connector-j")
    implementation("org.flywaydb:flyway-core")
    runtimeOnly("org.flywaydb:flyway-mysql:9.1.6")
    implementation("net.javacrumbs.shedlock:shedlock-spring:5.9.0")
    implementation("net.javacrumbs.shedlock:shedlock-provider-jdbc-template:5.9.0")
}

tasks {
    jar {
        enabled = true
    }
    bootJar {
        enabled = false
    }
}
