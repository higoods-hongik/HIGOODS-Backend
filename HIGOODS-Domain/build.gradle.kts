import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    kotlin("plugin.noarg")
    kotlin("kapt")
}

dependencies {
    implementation(project(Modules.INFRA))
    implementation(project(Modules.COMMON))

    val kapt by configurations
    // querydsl
    api(Dependencies.QUERYDSL)
    kapt(Dependencies.QUERYDSL_PROCESSOR)
    runtimeOnly(Dependencies.MARIA_DB_CLEINT)
    implementation(Dependencies.HIBERNATE_SPATIAL)
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
