dependencies {
    implementation(project(":HIGOODS-Domain"))
    implementation(project(":HIGOODS-Infra"))
    implementation(project(":HIGOODS-Common"))

    implementation(Dependencies.SPRING_WEB)
    implementation(Dependencies.SPRING_SECURITY)
    implementation(Dependencies.JWT)
}
