plugins {
  kotlin("jvm") version "2.2.20"
}

repositories {
  mavenCentral()
}

dependencies {
  implementation("io.strikt:strikt-core:0.34.0")
  implementation("tools.aqua:z3-turnkey:4.14.1")
}

sourceSets {
  main {
    kotlin.srcDir("src")
  }
}

tasks {
  wrapper {
    gradleVersion = "9.0.0"
  }
}
