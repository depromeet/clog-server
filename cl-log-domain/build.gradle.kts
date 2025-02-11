dependencies {
    implementation(project(":cl-log-global-utils"))
    implementation(project(":cl-log-infrastructure"))
}

tasks {
    jar {
        enabled = true
    }
    bootJar {
        enabled = false
    }
}
