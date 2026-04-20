plugins {
    id("uk.co.jasonmarston.project.standards.quarkus-library")
}

dependencies {
    api(project(":application-wiring"))
    api("io.smallrye.reactive:mutiny")
}
