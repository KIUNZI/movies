plugins {
    id("io.freefair.lombok")
    id("uk.co.jasonmarston.project.standards.quarkus-library")
}

dependencies {
    implementation("uk.co.jasonmarston.kiunzi:utility-domain-exception:0.0.0-SNAPSHOT")
    implementation("uk.co.jasonmarston.kiunzi:utility-validator:0.0.0-SNAPSHOT")
    implementation("uk.co.jasonmarston.kiunzi:utility-invariant:0.0.0-SNAPSHOT")
    implementation("io.quarkus:quarkus-jackson")
    implementation("io.quarkus:quarkus-hibernate-validator")
}


