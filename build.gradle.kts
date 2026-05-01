plugins {
    id("io.quarkus") apply false
    id("io.freefair.lombok") version "9.5.0" apply false
    id("org.kordamp.gradle.jandex") version "2.3.0" apply false
    id("org.gradlex.extra-java-module-info") version "1.14" apply false
    id("uk.co.jasonmarston.project.standards.java-library") version "1.0.8" apply false
    id("uk.co.jasonmarston.project.standards.quarkus-library") version "1.0.8" apply false
    id("uk.co.jasonmarston.project.standards.quarkus-app") version "1.0.8" apply false
    id("uk.co.jasonmarston.project.standards.liquibase") version "1.0.8" apply false
    id("uk.co.jasonmarston.project.standards.version") version "1.0.8"
    id("uk.co.jasonmarston.project.standards.gitops") version "1.0.8"
}

gitOpsPromote {
    gitOpsRepoDir.set(layout.projectDirectory.dir("../gitops"))
    appPath.set("apps/movies")
    devValuesPath.set("apps/movies/env/Dev/values.yaml")
    preProdValuesPath.set("apps/movies/env/PreProd/values.yaml")
    prodValuesPath.set("apps/movies/env/Prod/values.yaml")
    gitPush.set(true)
}
