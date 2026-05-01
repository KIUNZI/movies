plugins {
    id("uk.co.jasonmarston.project.standards.quarkus-library")
}

description = "Output contracts that define persistence and external integration boundaries."

dependencies {
    api(project(":domain"))
    api("io.smallrye.reactive:mutiny")
}
