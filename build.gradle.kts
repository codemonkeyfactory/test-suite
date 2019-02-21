plugins {
    val springDmVersion = "1.0.6.RELEASE"
    val kotlinVersion = "1.3.21"
    val dokkaVersion = "0.9.17"
    val testSetsVersion = "2.1.1"
    val sonarQubeVersion = "2.7"

    id("io.spring.dependency-management") version springDmVersion apply false
    kotlin("jvm") version kotlinVersion apply false
    kotlin("kapt") version kotlinVersion apply false
    id("org.jetbrains.dokka") version dokkaVersion apply false
    id("org.unbroken-dome.test-sets") version testSetsVersion apply false
    id("org.sonarqube") version sonarQubeVersion apply false
}

val gradleVersion by extra("5.2.1")
val kotlinVersion by extra {
    buildscript.configurations["classpath"]
        .resolvedConfiguration.firstLevelModuleDependencies
        .find { it.moduleName == "org.jetbrains.kotlin.jvm.gradle.plugin" }?.moduleVersion
}
val dependencyManagementPluginVersion by extra("1.0.6.RELEASE")
val junit5Version by extra("5.3.2")
val mockkVersion by extra { "1.9" }
val assertjVersion by extra { "3.11.1" }
val kotlinLoggingVersion by extra { "1.6.23" }
val log4j2Version by extra("2.11.1")
val jacksonVersion by extra { "2.9.8" }
val jacocoVersion by extra { "0.8.3" }

tasks {
    val gradleVersionExtra = gradleVersion // gradleVersion is shadowed inside wrapper task

    withType<Wrapper> {
        gradleVersion = gradleVersionExtra
        distributionType = Wrapper.DistributionType.ALL
    }
}

allprojects {
    group = "com.codemonkeyfactory.test"

    repositories {
        mavenLocal()
        mavenCentral()
        jcenter()
    }
}