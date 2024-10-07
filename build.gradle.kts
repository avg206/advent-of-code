plugins {
  kotlin("jvm") version "1.9.21"
}

repositories {
  mavenCentral()
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
