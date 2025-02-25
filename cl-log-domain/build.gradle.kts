dependencies {
    implementation(project(":cl-log-global-utils"))
}

tasks {
    jar {
        enabled = true
    }
    bootJar {
        enabled = false
    }
}
