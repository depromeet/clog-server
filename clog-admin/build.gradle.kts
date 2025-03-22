dependencies {
    implementation(project(":clog-global-utils"))
    implementation(project(":clog-domain"))
    implementation(project(":clog-infrastructure"))

    implementation("org.springframework.boot:spring-boot-devtools")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-security")
}

tasks {
    jar {
        enabled = false
    }
    bootJar {
        enabled = true
    }
}
