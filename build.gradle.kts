import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version PluginVersions.SPRING_BOOT_VERSION apply false
    id("io.spring.dependency-management") version PluginVersions.DEPENDENCY_MANAGER_VERSION apply false
    kotlin("jvm") version PluginVersions.JVM_VERSION
    kotlin("plugin.spring") version PluginVersions.SPRING_PLUGIN_VERSION apply false
    kotlin("plugin.jpa") version PluginVersions.JPA_PLUGIN_VERSION apply false
    id("org.jlleitschuh.gradle.ktlint") version PluginVersions.KTLINT_VERSIOM
    kotlin("kapt") version PluginVersions.KAPT_VERSION apply false
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

allprojects {
    group = "com.higoods"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
    }
    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs += "-Xjsr305=strict"
            jvmTarget = "17"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    dependencies {
        implementation(Dependencies.SPRING_DATA_JPA)
        implementation(Dependencies.JACKSON)
        implementation(Dependencies.KOTLIN_REFLECT)
        implementation(Dependencies.KOTLIN_LOGGING)

        testImplementation(Dependencies.SPRING_TEST)
        testImplementation(Dependencies.KOTEST_RUNNER_JUNIT5)
        testImplementation(Dependencies.KOTEST_ASSERTIONS_CORE)
        testImplementation(Dependencies.KOTEST_FRAMEWORK_DATATEST)
        testImplementation(Dependencies.KOTEST_EXTENSION_SPRING)
        testImplementation(Dependencies.MOCKK_DEFAULT)
        testImplementation(Dependencies.MOCKK_SPRING)
    }

    tasks.getByName<Jar>("jar") {
        enabled = false
    }
}
