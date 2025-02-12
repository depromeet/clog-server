dependencies {
    implementation(project(":cl-log-global-utils"))
    implementation(project(":cl-log-domain"))

    implementation("org.springframework.boot:spring-boot-starter-web")
}

tasks {
    jar {
        enabled = false
    }
    bootJar {
        enabled = true
    }
}
