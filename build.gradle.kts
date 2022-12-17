val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val flyway_version: String by project
val posgresql_version: String by project
val hikaricp_version: String by project
val exposed_version: String by project

plugins {
    kotlin("jvm") version "1.7.22"
    id("io.ktor.plugin") version "2.2.1"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.7.22"
    id("com.jetbrains.exposed.gradle.plugin") version "0.2.1"

}

group = "com.task"
version = "0.0.1"

sourceSets.main {
    java.srcDir("build/tables")
}
application {
    mainClass.set("com.test.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}



dependencies {
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-swagger:$ktor_version")
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")
    implementation("io.ktor:ktor-server-content-negotiation:$ktor_version")
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
    implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-dao:$exposed_version")
    implementation("org.flywaydb:flyway-core:$flyway_version")
    implementation("org.postgresql:postgresql:$posgresql_version")
    implementation("com.zaxxer:HikariCP:$hikaricp_version")
}