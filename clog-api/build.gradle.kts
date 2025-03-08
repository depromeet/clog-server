dependencies {
    implementation(project(":clog-global-utils"))
    implementation(project(":clog-domain"))
    implementation(project(":clog-infrastructure"))

    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.5")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")

    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    implementation("com.auth0:java-jwt:4.4.0")
    implementation("com.auth0:jwks-rsa:0.20.0")
}

tasks {
    jar {
        enabled = false
    }
    bootJar {
        enabled = true
    }
}
