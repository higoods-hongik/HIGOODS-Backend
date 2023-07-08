dependencies {
    implementation(project(":HIGOODS-Domain"))
    implementation(project(":HIGOODS-Infra"))
    implementation(project(":HIGOODS-Common"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("com.auth0:java-jwt:4.2.1")
}
