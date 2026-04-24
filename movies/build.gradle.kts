import uk.co.jasonmarston.build.utility.configString
import uk.co.jasonmarston.build.utility.timestamp

plugins {
    id("uk.co.jasonmarston.project.standards.quarkus-app")
    id("uk.co.jasonmarston.project.standards.liquibase")
    id("org.liquibase.gradle") version "3.1.0"
}

buildscript {
    dependencies {
        classpath("org.liquibase:liquibase-core:5.0.2")
    }
}

val quarkusProfile = providers.configString("quarkus.profile", true).orElse("dev")
val dbUsername = providers.configString("quarkus.datasource.username")
val dbPassword = providers.configString("quarkus.datasource.password")
val dbUrl = providers.configString("quarkus.datasource.jdbc.url")

dependencies {
    implementation(project(":input-adaptor"))
    implementation(project(":output-adaptor"))

    implementation("io.quarkus:quarkus-config-yaml")
    implementation("io.quarkus:quarkus-container-image-jib")
    implementation("io.quarkus:quarkus-hibernate-reactive-panache")
    implementation("io.quarkus:quarkus-oidc")
    implementation("io.quarkus:quarkus-rest-jackson")

    implementation("io.quarkus:quarkus-reactive-mysql-client")

    when (quarkusProfile.get()) {
        "prod" -> {
            implementation("io.quarkiverse.vault:quarkus-vault")
            implementation("io.quarkus:quarkus-smallrye-health")
        }
        "dev" -> {
            implementation("io.quarkus:quarkus-liquibase")
            implementation("io.quarkus:quarkus-jdbc-mysql")
        }
    }

    liquibaseRuntime("org.liquibase:liquibase-core:5.0.2")
    liquibaseRuntime("org.liquibase.ext:liquibase-hibernate6:5.0.2")
    liquibaseRuntime("org.hibernate.orm:hibernate-core:6.6.15.Final")
    liquibaseRuntime("info.picocli:picocli:4.7.7")
    liquibaseRuntime("org.springframework:spring-context:6.2.10")
    liquibaseRuntime("org.springframework:spring-orm:6.2.10")
    liquibaseRuntime("com.mysql:mysql-connector-j:9.6.0")
    liquibaseRuntime(files(rootProject
        .project(":output-adaptor")
        .layout
        .buildDirectory
        .dir("classes/java/main"))
    )
    liquibaseRuntime(files(rootProject
        .project(":domain")
        .layout
        .buildDirectory
        .dir("classes/java/main"))
    )
}

liquibase {
    activities.register("baseline") {
        this.arguments = mapOf(
            "url" to dbUrl.get(),
            "username" to dbUsername.get(),
            "password" to dbPassword.get(),
            "driver" to "com.mysql.cj.jdbc.Driver",
            "changelogFile" to "movies/src/main/resources/db/changelog/baseline/db.changelog-baseline.yaml",
            "logLevel" to "info"
        )
    }
    activities.register("diff") {
        this.arguments = mapOf(
            "url" to dbUrl.get(),
            "username" to dbUsername.get(),
            "password" to dbPassword.get(),
            "driver" to "com.mysql.cj.jdbc.Driver",
            "changelogFile" to "movies/src/main/resources/db/changelog/db.changelog-master.yaml",
            "diffChangelogFile" to "movies/src/main/resources/db/changelog/changes/${timestamp()}-diff.yaml",
            "referenceUrl" to "hibernate:spring:uk.co.jasonmarston.movies.output.adaptor.data?dialect=org.hibernate.dialect.MySQLDialect",
            "referenceDriver" to "liquibase.ext.hibernate.database.connection.HibernateDriver",
            "logLevel" to "info"
        )
    }
    runList = "diff"
}
