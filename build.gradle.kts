val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val postgres_version: String by project
val exposed_version: String by project
val hikari_version: String by project
val koin_version: String by project
val jbcrypt_version: String by project
val google_api_version: String by project
val apache_version: String by project
val firebase_admin_version: String by project

plugins {
    kotlin("jvm") version "1.9.10"
    id("io.ktor.plugin") version "2.3.5"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.10"
}

group = "com.itami"
version = "0.0.1"

application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

ktor {
    fatJar {
        archiveFileName.set("ktor-calorie-tracker.jar")
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm")
    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("io.ktor:ktor-server-call-logging-jvm")
    implementation("io.ktor:ktor-server-host-common-jvm")
    implementation("io.ktor:ktor-server-status-pages-jvm")
    implementation("io.ktor:ktor-server-rate-limit")
    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("io.ktor:ktor-server-request-validation")
    implementation("io.ktor:ktor-server-auth")
    implementation("io.ktor:ktor-server-auth-jwt")
    testImplementation("io.ktor:ktor-server-tests-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")

    // Logback
    implementation("ch.qos.logback:logback-classic:$logback_version")

    // Google Client API Library
    implementation("com.google.api-client:google-api-client:$google_api_version")

    // JBCrypt
    implementation("org.mindrot:jbcrypt:$jbcrypt_version")

    // Koin Dependency Injection
    implementation("io.insert-koin:koin-core:$koin_version")
    implementation("io.insert-koin:koin-ktor:$koin_version")

    // For sending emails
    implementation("org.apache.commons:commons-email:$apache_version")

    // Firebase
    implementation("com.google.firebase:firebase-admin:$firebase_admin_version")

    // Database
    implementation("org.postgresql:postgresql:$postgres_version")
    implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-dao:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-java-time:$exposed_version")
    implementation ("com.zaxxer:HikariCP:$hikari_version")

}
