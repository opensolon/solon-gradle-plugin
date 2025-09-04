buildscript {
    repositories {
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    kotlin("jvm") version "1.8.0" apply false
    id("com.vanniktech.maven.publish") version "0.24.0" apply false
}

allprojects {
    repositories {
        mavenLocal()
        mavenCentral()
    }
}
