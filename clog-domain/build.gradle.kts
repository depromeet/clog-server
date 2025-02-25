dependencies {
    implementation(project(":clog-global-utils"))
    implementation(project(":clog-infrastructure"))
}

tasks {
    jar {
        enabled = true
    }
    bootJar {
        enabled = false
    }
}
