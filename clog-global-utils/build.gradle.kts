dependencies {
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.5")
}

tasks {
    jar {
        enabled = true
    }
    bootJar {
        enabled = false
    }
}
