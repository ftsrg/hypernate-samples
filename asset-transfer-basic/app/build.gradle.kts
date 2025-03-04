import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("buildlogic.java-application-conventions")
    alias(libs.plugins.lombok)
    alias(libs.plugins.shadow)
    alias(libs.plugins.spotless)
}

version = "0.1.0"

dependencies {
    implementation(libs.fabric.chaincode.shim)
    implementation(project(":hypernate:lib"))
}

repositories {
    maven { setUrl("https://jitpack.io") }
}

application {
    mainClass = "org.hyperledger.fabric.contract.ContractRouter"
}

tasks.named<ShadowJar>("shadowJar") {
    archiveBaseName = "chaincode"
    archiveClassifier = ""
    archiveVersion = ""
    mergeServiceFiles() // to make sure the right NameResolverProvider will be used

    manifest {
        attributes(mapOf("Main-Class" to application.mainClass))
    }
}