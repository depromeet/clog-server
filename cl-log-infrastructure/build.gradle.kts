dependencies {
    implementation(project(":cl-log-global-utils"))
    implementation(project(":cl-log-domain"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("com.github.gavlyukovskiy:p6spy-spring-boot-starter:1.10.0")

    implementation("com.linecorp.kotlin-jdsl:jpql-dsl:3.5.4")
    implementation("com.linecorp.kotlin-jdsl:jpql-render:3.5.4")

    runtimeOnly("com.mysql:mysql-connector-j")
}

tasks {
    jar {
        enabled = true
    }
    bootJar {
        enabled = false
    }
}
