dependencies {
    implementation(project(":clog-global-utils"))
    implementation(project(":clog-domain"))
    implementation(project(":clog-infrastructure"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.5")
}

tasks {
    jar {
        enabled = false
    }
    bootJar {
        enabled = true
    }
}


