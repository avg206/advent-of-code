plugins {
  kotlin("jvm") version "1.9.21"
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
