dependencies {
    implementation(project(":clog-global-utils"))
    implementation(project(":clog-domain"))
    implementation(project(":clog-infrastructure"))

    implementation("org.thymeleaf:thymeleaf:2.0.5")
}

tasks {
    jar {
        enabled = false
    }
    bootJar {
        enabled = true
    }
}
