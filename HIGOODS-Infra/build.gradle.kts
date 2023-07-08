import org.springframework.boot.gradle.tasks.bundling.BootJar

dependencies {
    implementation(project(":HIGOODS-Common"))

    api("org.springframework.boot:spring-boot-starter-data-redis")
    api("org.redisson:redisson:3.19.0")
}

tasks {
    withType<Jar> { enabled = true }
    withType<BootJar> { enabled = false }
}
