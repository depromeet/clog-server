dependencies {
    implementation(project(":clog-global-utils"))
    implementation(project(":clog-domain"))
    implementation(project(":clog-infrastructure"))

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
}

tasks {
    jar {
        enabled = false
    }
    bootJar {
        enabled = true
    }
}
