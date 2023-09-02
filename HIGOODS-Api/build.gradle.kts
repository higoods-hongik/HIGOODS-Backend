import org.hidetake.gradle.swagger.generator.GenerateSwaggerUI
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    // restdocs-api-spec
    id("com.epages.restdocs-api-spec") version "0.16.4"
    // swagger
    id("org.hidetake.swagger.generator") version "2.18.2"
}

openapi3 {
    setServer("http://localhost:8080/api")
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
    runtimeOnly(Dependencies.JWT_JACKSON)
    runtimeOnly(Dependencies.JWT_IMPL)

    testImplementation(Dependencies.REST_DOC)
    testImplementation(Dependencies.REST_DOC_API_SPEC)
    swaggerUI(Dependencies.SWAGGER_UI)

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
            into("src/main/resources/static/docs")
        }
        withType<BootJar> {
            enabled = true
            dependsOn("copySwaggerUI")
        }
    }
}
