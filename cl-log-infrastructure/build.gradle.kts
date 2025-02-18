dependencies {
    implementation(project(":cl-log-global-utils"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("com.github.gavlyukovskiy:p6spy-spring-boot-starter:1.10.0")

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
