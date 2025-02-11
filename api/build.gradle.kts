dependencies {
    implementation(project(":global-utils"))
    implementation(project(":domain"))

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
