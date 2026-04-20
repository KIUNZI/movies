pluginManagement {
    includeBuild("../settings-plugin")
}

plugins {
    id("uk.co.jasonmarston.standards.settings")
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

rootProject.name = "movies"

include(
    "domain",
    "input-port",
    "input-adaptor",
    "lib",
    "movies",
    "output-port",
    "output-adaptor",
    "application-wiring"
)
