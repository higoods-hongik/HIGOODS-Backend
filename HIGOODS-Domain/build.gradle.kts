import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    kotlin("plugin.noarg")
}

dependencies {
    implementation(project(":HIGOODS-Infra"))
    implementation(project(":HIGOODS-Common"))

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    runtimeOnly("org.mariadb.jdbc:mariadb-java-client")
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.Embeddable")
    annotation("javax.persistence.MappedSuperclass")
}

noArg {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.Embeddable")
    annotation("javax.persistence.MappedSuperclass")
}

tasks {
    withType<Jar> { enabled = true }
    withType<BootJar> { enabled = false }
}
