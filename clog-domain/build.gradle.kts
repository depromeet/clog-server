dependencies {
    implementation(project(":clog-global-utils"))

    implementation("org.hibernate.orm:hibernate-core")
    implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")
    implementation("com.auth0:java-jwt:4.4.0")
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")
    implementation("com.auth0:jwks-rsa:0.20.0")
}

tasks {
    jar {
        enabled = true
    }
    bootJar {
        enabled = false
    }
}
