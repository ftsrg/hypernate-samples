/* SPDX-License-Identifier: Apache-2.0 */
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
  java
  application
  alias(libs.plugins.lombok)
  alias(libs.plugins.shadow)
  alias(libs.plugins.spotless)
}

repositories {
  mavenCentral()
  maven { setUrl("https://jitpack.io") }
}

dependencies {
  implementation(libs.fabric.chaincode.shim)
  implementation(files("libs/hypernate-0.1.0.jar"))

  testImplementation(libs.junit.jupiter)

  testRuntimeOnly(libs.junit.platform.launcher)
}

java { toolchain { languageVersion = JavaLanguageVersion.of(17) } }

application { mainClass = "org.hyperledger.fabric.contract.ContractRouter" }

tasks.named<ShadowJar>("shadowJar") {
  archiveBaseName = "chaincode"
  archiveClassifier = ""
  archiveVersion = ""
  mergeServiceFiles() // to make sure the right NameResolverProvider will be used

  manifest { attributes(mapOf("Main-Class" to application.mainClass)) }
}

tasks.test {
  // use JUnit Platform for unit tests
  useJUnitPlatform()
}

spotless {
  java {
    importOrder()
    removeUnusedImports()
    googleJavaFormat()
    formatAnnotations()
    toggleOffOn()
    licenseHeader("/* SPDX-License-Identifier: Apache-2.0 */", "package ")
  }
  kotlin {
    target("src/*/kotlin/**/*.kt", "buildSrc/src/*/kotlin/**/*.kt")
    ktfmt()
    licenseHeader("/* SPDX-License-Identifier: Apache-2.0 */", "package ")
  }
  kotlinGradle { ktfmt() }
}
