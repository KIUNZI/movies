plugins {
    id("uk.co.jasonmarston.project.standards.quarkus-library")
}

dependencies {
    implementation(project(":input-port"))
    implementation("uk.co.jasonmarston.kiunzi:utility-producer:0.0.0-SNAPSHOT")
    implementation("uk.co.jasonmarston.kiunzi:utility-exception-mapper:0.0.0-SNAPSHOT")
    implementation("io.quarkus:quarkus-rest-jackson-common")
    implementation("io.quarkus:quarkus-hibernate-validator")
    implementation("org.modelmapper:modelmapper")
}
