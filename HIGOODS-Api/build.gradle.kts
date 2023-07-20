import org.hidetake.gradle.swagger.generator.GenerateSwaggerUI
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    // restdocs-api-spec
    id("com.epages.restdocs-api-spec") version "0.16.4"
    // swagger
    id("org.hidetake.swagger.generator") version "2.18.2"
}

openapi3 {
    setServer("http://localhost:8080")
    title = "HIGOODS API Documentation"
    description = "Spring REST Docs with SwaggerUI"
    version = "0.0.1"
    format = "yaml"
}

swaggerSources {
    create("ApiDocument").apply {
        setInputFile(file("${project.buildDir}/api-spec/openapi3.yaml"))
    }
}

dependencies {
    implementation(project(Modules.DOMAIN))
    implementation(project(Modules.INFRA))
    implementation(project(Modules.COMMON))

    implementation(Dependencies.SPRING_SECURITY)
    implementation(Dependencies.JWT)
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("com.auth0:java-jwt:4.2.1")

    // restdocs-api-spec
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    testImplementation("com.epages:restdocs-api-spec-mockmvc:0.16.4")
    // swagger ui
    swaggerUI("org.webjars:swagger-ui:4.11.1")

    tasks {
        withType<Jar> { enabled = false }
        withType<GenerateSwaggerUI> {
            dependsOn("openapi3")
        }
        register<Copy>("copySwaggerUI") {
            dependsOn("generateSwaggerUIApiDocument")
            mustRunAfter("jacocoTestReport")

            val generateSwaggerUISampleTask =
                this@tasks.named<GenerateSwaggerUI>("generateSwaggerUIApiDocument").get()
            from("${generateSwaggerUISampleTask.outputDir}")
            into("${project.buildDir}/resources/main/static/docs")
        }
        withType<BootJar> {
            enabled = true
            dependsOn("copySwaggerUI")
        }
    }
}
