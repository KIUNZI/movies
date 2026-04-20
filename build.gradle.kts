plugins {
    id("io.quarkus") version "3.32.3" apply false
    id("io.freefair.lombok") version "9.2.0" apply false
    id("org.kordamp.gradle.jandex") version "2.3.0" apply false
    id("org.gradlex.extra-java-module-info") version "1.14" apply false
    id("uk.co.jasonmarston.project.standards.java-library") version "1.1.1" apply false
    id("uk.co.jasonmarston.project.standards.quarkus-library") version "1.1.1" apply false
    id("uk.co.jasonmarston.project.standards.quarkus-app") version "1.1.1" apply false
    id("uk.co.jasonmarston.project.standards.liquibase") version "1.1.1" apply false
    id("uk.co.jasonmarston.project.standards.version") version "1.1.1"
    id("uk.co.jasonmarston.project.standards.gitops") version "1.1.1"
}

gitOpsPromote {
    gitOpsRepoDir.set(layout.projectDirectory.dir("../gitops-repo"))
    appPath.set("apps/kiunzi-movies")
    devValuesPath.set("apps/kiunzi-movies/env/Dev/values.yaml")
    preProdValuesPath.set("apps/kiunzi-movies/env/PreProd/values.yaml")
    prodValuesPath.set("apps/kiunzi-movies/env/Prod/values.yaml")
    gitPush.set(true)
}
