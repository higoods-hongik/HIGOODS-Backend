import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    kotlin("plugin.noarg")
    kotlin("kapt")
}

dependencies {
    implementation(project(":HIGOODS-Infra"))
    implementation(project(":HIGOODS-Common"))

    val kapt by configurations
    // querydsl
    api("com.querydsl:querydsl-jpa:5.0.0")
    kapt("com.querydsl:querydsl-apt:5.0.0:jpa")

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
