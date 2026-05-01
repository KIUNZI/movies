plugins {
    id("uk.co.jasonmarston.project.standards.quarkus-library")
}

description = "HTTP-facing adapters that translate transport requests into input-port commands."

dependencies {
    implementation(project(":input-port"))
    implementation("uk.co.jasonmarston.kiunzi:utility-producer:1.0.0")
    implementation("uk.co.jasonmarston.kiunzi:utility-exception-mapper:1.0.0")
    implementation("io.quarkus:quarkus-rest-jackson-common")
    implementation("io.quarkus:quarkus-hibernate-validator")
    implementation("org.modelmapper:modelmapper")
}
