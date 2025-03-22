dependencies {
    implementation(project(":clog-global-utils"))

    implementation("org.hibernate.orm:hibernate-core")
    implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")
}

tasks {
    jar {
        enabled = true
    }
    bootJar {
        enabled = false
    }
}
