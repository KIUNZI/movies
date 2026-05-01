plugins {
    id("io.freefair.lombok")
    id("uk.co.jasonmarston.project.standards.quarkus-library")
}

dependencies {
    implementation(project(":output-port"))
    implementation("uk.co.jasonmarston.kiunzi:utility-producer:1.0.0")
    implementation("uk.co.jasonmarston.kiunzi:utility-domain-exception:1.0.0")
    implementation("io.quarkus:quarkus-hibernate-reactive-panache")
    implementation("io.quarkus:quarkus-hibernate-validator")
    implementation("org.modelmapper:modelmapper")
}

