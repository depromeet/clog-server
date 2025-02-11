dependencies {
    implementation(project(":global-utils"))
    implementation(project(":infrastructure"))
}

tasks {
    jar {
        enabled = true
    }
    bootJar {
        enabled = false
    }
}
