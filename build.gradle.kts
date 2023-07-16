import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version PluginVersions.SPRING_BOOT_VERSION apply false
    id("io.spring.dependency-management") version PluginVersions.DEPENDENCY_MANAGER_VERSION apply false
    kotlin("jvm") version PluginVersions.JVM_VERSION
    kotlin("plugin.spring") version PluginVersions.SPRING_PLUGIN_VERSION apply false
    kotlin("plugin.jpa") version PluginVersions.JPA_PLUGIN_VERSION apply false
    id("org.jlleitschuh.gradle.ktlint") version PluginVersions.KTLINT_VERSIOM
    kotlin("kapt") version PluginVersions.KAPT_VERSION apply false
    id("org.sonarqube") version PluginVersions.SONAR_VERSION
    id("jacoco")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

sonarqube {
    properties {
        property("sonar.projectKey", "higoods-hongik_HIGOODS-Backend")
        property("sonar.organization", "higoods-api")
        property("sonar.host.url", "https://sonarcloud.io")
        // sonar additional settings
        property("sonar.sources", "src")
        property("sonar.language", "Kotlin")
        property("sonar.sourceEncoding", "UTF-8")
        property("sonar.test.inclusions", "**/*Test.java")
        property("sonar.exclusions", "**/test/**, **/Q*.kt, **/*Doc*.kt, **/resources/** ,**/*Application*.kt , **/*Config*.kt, **/*Dto*.kt, **/*Request*.kt, **/*Response*.kt ,**/*Exception*.kt ,**/*ErrorCode*.kt")
        property("sonar.java.coveragePlugin", "jacoco")
    }
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
    apply(plugin = "jacoco")

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
    tasks.test {
        finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run
    }
    tasks.jacocoTestReport {
        dependsOn(tasks.test) // tests are required to run before generating the report
        reports {
            html.required.set(true) // html 설정
            csv.required.set(true) // csv 설정
            xml.required.set(true)
            xml.outputLocation.set(File("$buildDir/reports/jacoco.xml"))
        }
        classDirectories.setFrom(
            files(
                classDirectories.files.map {
                    fileTree(it) { // 테스트 커버리지 측정 제외 목록
                        exclude(
                            "**/*Application*",
                            "**/*Config*",
                            "**/*Dto*",
                            "**/*Request*",
                            "**/*Response*",
                            "**/*Interceptor*",
                            "**/*Exception*",
                            "**/Q*"
                        ) // QueryDsl 용이나 Q로 시작하는 클래스 뺄 위험 존재
                    }
                }
            )
        )
    }

    sonarqube {
        properties {
            property("sonar.java.binaries", "$buildDir/classes")
            property("sonar.coverage.jacoco.xmlReportPaths", "$buildDir/reports/jacoco.xml")
        }
    }
}
