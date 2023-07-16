import org.springframework.boot.gradle.tasks.bundling.BootJar

dependencies {
    implementation(project(Modules.COMMON))

    api(Dependencies.SPRING_REDIS)
    api(Dependencies.REDISSON)
    api(Dependencies.OPEN_FEIGN)
}

tasks {
    withType<Jar> { enabled = true }
    withType<BootJar> { enabled = false }
}
