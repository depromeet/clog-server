dependencies {
    implementation(project(":clog-global-utils"))
    implementation(project(":clog-domain"))

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


