plugins {
    id("io.freefair.lombok")
    id("uk.co.jasonmarston.project.standards.quarkus-library")
}

description = "Domain model, invariants, and shared value objects for the movies service."

dependencies {
    implementation("uk.co.jasonmarston.kiunzi:utility-domain-exception:1.0.0")
    implementation("uk.co.jasonmarston.kiunzi:utility-validator:1.0.0")
    implementation("uk.co.jasonmarston.kiunzi:utility-invariant:1.0.0")
    implementation("io.quarkus:quarkus-jackson")
    implementation("io.quarkus:quarkus-hibernate-validator")
}


