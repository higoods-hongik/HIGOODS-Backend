import org.springframework.boot.gradle.tasks.bundling.BootJar

dependencies {
    implementation(project(":HIGOODS-Common"))

    api(Dependencies.SPRING_REDIS)
    api(Dependencies.REDISSON)
}

tasks {
    withType<Jar> { enabled = true }
    withType<BootJar> { enabled = false }
}
