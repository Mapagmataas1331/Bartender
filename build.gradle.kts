// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
  id("com.android.application") version "8.1.4" apply false
  id("com.google.devtools.ksp") version "1.9.20-1.0.14" apply false
}

buildscript {
  val composeVersion by extra("1.5.4")
  val kotlinVersion by extra("1.9.20")
  val hiltVersion by extra("2.48.1")

  repositories {
    google()
    mavenCentral()
  }

  dependencies {
    classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
    classpath("com.google.dagger:hilt-android-gradle-plugin:$hiltVersion")
  }
}

tasks.register("clean", Delete::class) {
  delete(rootProject.buildDir)
}