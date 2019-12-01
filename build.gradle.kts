plugins {
    kotlin("jvm") version "1.2.41"
    java
}

repositories {
    jcenter()
    maven("https://jitpack.io")
}

dependencies {
    implementation(kotlin("stdlib"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.5.2")
    testImplementation("com.natpryce:hamkrest:1.7.0.0")
}

tasks.withType<Test> {
    useJUnitPlatform()
}