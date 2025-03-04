plugins {
    java
}

repositories {
    mavenCentral()
}

dependencies {
    // use JUnit Jupiter for testing
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.3")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

tasks.named<Test>("test") {
    // use JUnit Platform for unit tests
    useJUnitPlatform()
}
