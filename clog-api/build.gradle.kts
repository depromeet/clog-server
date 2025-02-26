dependencies {
    implementation(project(":clog-global-utils"))
    implementation(project(":clog-domain"))
    implementation(project(":clog-infrastructure"))

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


