plugins {
    id("io.freefair.lombok")
    id("uk.co.jasonmarston.project.standards.quarkus-library")
}

dependencies {
//    api(project(":application-wiring"))
    api(project(":domain"))
    implementation("uk.co.jasonmarston.kiunzi:utility-producer:0.0.0-SNAPSHOT")
    implementation("uk.co.jasonmarston.kiunzi:utility-validator:0.0.0-SNAPSHOT")
    implementation("uk.co.jasonmarston.kiunzi:utility-domain-exception:0.0.0-SNAPSHOT")
    implementation(project(":output-port"))
    api("io.smallrye.reactive:mutiny")
    implementation("io.quarkus:quarkus-arc")
    implementation("io.quarkus:quarkus-hibernate-validator")
    implementation("org.modelmapper:modelmapper")
}
