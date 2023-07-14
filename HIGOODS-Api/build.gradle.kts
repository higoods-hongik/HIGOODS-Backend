import org.hidetake.gradle.swagger.generator.GenerateSwaggerUI
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    // restdocs-api-spec
    id("com.epages.restdocs-api-spec") version "0.18.0"
    // swagger
    id("org.hidetake.swagger.generator") version "2.19.2"
}

dependencies {
    implementation(project(":HIGOODS-Domain"))
    implementation(project(":HIGOODS-Infra"))
    implementation(project(":HIGOODS-Common"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("com.auth0:java-jwt:4.2.1")

    // restdocs-api-spec
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc:3.0.0")
    testImplementation("com.epages:restdocs-api-spec-mockmvc:0.18.0")
    // swagger ui
    swaggerUI("org.webjars:swagger-ui:4.1.3")

    tasks {
        openapi3 {
            setServer("http://localhost:8080")
            title = "restdocs-swagger Test API Documentation"
            description = "Spring REST Docs with SwaggerUI"
            version = "0.0.1"
            format = "yaml"
        }

        swaggerSources {
            register("ApiDocument").configure {
                setInputFile(file("${rootProject.buildDir}/api-spec/openapi3.yaml"))
            }
        }
        withType<Jar> { enabled = false }
        withType<GenerateSwaggerUI> {
            dependsOn("openapi3")
        }
        register<Copy>("copySwaggerUI") {
            dependsOn("generateSwaggerUIApiDocument")

            from(project.tasks.getByName<GenerateSwaggerUI>("generateSwaggerUIApiDocument").outputDir)
            into("src/main/resources/static/swagger")
        }
        withType<BootJar> {
            enabled = true
            dependsOn("copySwaggerUI")
        }
    }
}
