plugins {
    id("uk.co.jasonmarston.project.standards.quarkus-library")
}

dependencies {
    api(project(":domain"))
    api("io.smallrye.reactive:mutiny")
}
