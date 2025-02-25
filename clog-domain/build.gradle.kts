dependencies {
    implementation(project(":clog-global-utils"))
}

tasks {
    jar {
        enabled = true
    }
    bootJar {
        enabled = false
    }
}
