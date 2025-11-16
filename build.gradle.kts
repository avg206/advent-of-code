plugins {
  kotlin("jvm") version "2.2.20"
}

repositories {
  mavenCentral()
}

dependencies {
  implementation("io.strikt:strikt-core:0.34.0")
}

sourceSets {
  main {
    kotlin.srcDir("src")
  }
}

tasks {
  wrapper {
    gradleVersion = "8.5"
  }
}
