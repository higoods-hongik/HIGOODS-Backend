dependencies {
    implementation(project(Modules.DOMAIN))
    implementation(project(Modules.INFRA))
    implementation(project(Modules.COMMON))

    implementation(Dependencies.SPRING_WEB)
    implementation(Dependencies.SPRING_SECURITY)
    implementation(Dependencies.JWT)
}
