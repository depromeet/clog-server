dependencies {
    implementation(project(":global-utils"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("com.mysql:mysql-connector-j")
}

tasks {
    jar {
        enabled = true
    }
    bootJar {
        enabled = false
    }
}
