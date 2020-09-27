import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  id("java")

  kotlin("jvm")
  kotlin("kapt")
  kotlin("plugin.spring")

  id("org.springframework.boot")
  id("io.spring.dependency-management")

  id("com.google.cloud.tools.jib")

  id("publishing")
  id("maven-publish")

  id("plugin-testing")
}

val apacheCXFVersion: String by project
val javaxXmlVersion: String by project
val sunXmlVersion: String by project
val javaxActivation: String by project
val springVersion: String by project
val amberleywayVersion: String by project
val sidwellcourtVersion: String by project
dependencies {

  kapt("org.springframework.boot", "spring-boot-configuration-processor", springVersion)

  implementation("org.springframework.boot", "spring-boot-starter-webflux", springVersion)
  implementation("com.fasterxml.jackson.module", "jackson-module-kotlin")

  implementation("org.apache.cxf", "cxf-spring-boot-starter-jaxws", apacheCXFVersion)

  implementation("org.springframework.ws", "spring-ws-core")

  implementation(kotlin("stdlib-jdk8", "1.3.61"))
//  implementation("com.github.johnlayton.amberleyway", "api", amberleywayVersion)
  implementation("com.github.johnlayton.amberleyway", "sal", amberleywayVersion)
  implementation("com.github.johnlayton.sidwellcourt", "sal", sidwellcourtVersion)

  testImplementation("org.springframework.boot:spring-boot-starter-test") {
    exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
  }

  testImplementation("com.github.JensPiegsa:wiremock-extension:0.4.0")
  testImplementation("org.junit.jupiter:junit-jupiter-api:5.5.2")
  testImplementation("org.junit.jupiter:junit-jupiter-engine:5.5.2")
  testImplementation("org.junit.jupiter:junit-jupiter-params:5.5.2")
  testImplementation("io.projectreactor:reactor-test")

  testImplementation("org.testcontainers:testcontainers:1.10.6")
  testImplementation("org.testcontainers:junit-jupiter:1.10.6")
  testImplementation("org.testcontainers:selenium:1.10.6")
}

tasks.getByName<Jar>("jar") {
  enabled = true
}

publishing {
  publications {
    create<MavenPublication>("bootJava") {
      artifact(tasks.getByName("bootJar"))
    }
  }
}

jib {
  to {
    image = "johnlayton/bondsroad"
    credHelper = "osxkeychain"
  }
}