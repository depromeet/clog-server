dependencies {
    implementation(project(":clog-global-utils"))
    implementation(project(":clog-domain"))

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("com.github.gavlyukovskiy:p6spy-spring-boot-starter:1.10.0")
    implementation("org.apache.httpcomponents.client5:httpclient5:5.4.2")

    implementation("com.linecorp.kotlin-jdsl:jpql-dsl:3.5.4")
    implementation("com.linecorp.kotlin-jdsl:jpql-render:3.5.4")

    implementation("com.h2database:h2")
    runtimeOnly("com.h2database:h2")

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
