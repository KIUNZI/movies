plugins {
    id("io.freefair.lombok")
    id("uk.co.jasonmarston.project.standards.quarkus-library")
}

description = "Application-layer input contracts and use-case orchestration for the movies service."

dependencies {
//    api(project(":application-wiring"))
    api(project(":domain"))
    implementation("uk.co.jasonmarston.kiunzi:utility-producer:1.0.0")
    implementation("uk.co.jasonmarston.kiunzi:utility-validator:1.0.0")
    implementation("uk.co.jasonmarston.kiunzi:utility-domain-exception:1.0.0")
    implementation(project(":output-port"))
    api("io.smallrye.reactive:mutiny")
    implementation("io.quarkus:quarkus-arc")
    implementation("io.quarkus:quarkus-hibernate-validator")
    implementation("org.modelmapper:modelmapper")
}
