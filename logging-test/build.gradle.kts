import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("io.spring.dependency-management")
    kotlin("jvm")
    kotlin("kapt")
    id("org.unbroken-dome.test-sets")
    jacoco
    id("org.sonarqube")
    id("maven-publish")
}

val integrationTestName = "integrationTest"

testSets {
    integrationTestName {
        dirName = integrationTestName.toKebabCase()
    }
}

kapt.includeCompileClasspath = false

val junit5Version: String by project
val mockkVersion: String by project
val assertjVersion: String by project
val kotlinLoggingVersion: String by project
val log4j2Version: String by project
val jacksonVersion: String by project
val jacocoVersion: String by project

dependencyManagement {
    imports {
        mavenBom("org.junit:junit-bom:$junit5Version")
        mavenBom("org.apache.logging.log4j:log4j-bom:$log4j2Version")
        mavenBom("com.fasterxml.jackson:jackson-bom:$jacksonVersion")
    }
}

val integrationTestImplementation: Configuration = configurations["${integrationTestName}Implementation"]

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))

    implementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.jupiter:junit-jupiter-engine")

    testImplementation("io.mockk:mockk:$mockkVersion")

    testImplementation("org.assertj:assertj-core:$assertjVersion")

    implementation("org.apache.logging.log4j:log4j-api")
    implementation("org.apache.logging.log4j:log4j-core")
    integrationTestImplementation("io.github.microutils:kotlin-logging:$kotlinLoggingVersion")
    integrationTestImplementation("org.apache.logging.log4j:log4j-slf4j-impl")
    integrationTestImplementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml")
    integrationTestImplementation("com.fasterxml.jackson.core:jackson-databind")
    kapt("org.apache.logging.log4j:log4j-core")
}

// kotlin options
val kotlinApiVersion by extra { "1.3" }
val kotlinLanguageVersion by extra { "1.3" }
val kotlinJvmTarget by extra { "1.8" }
val kotlinFreeCompilerArgs by extra { listOf("-version", "-Xjsr305=strict") }

jacoco {
    toolVersion = jacocoVersion
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            apiVersion = kotlinApiVersion
            languageVersion = kotlinLanguageVersion
            jvmTarget = kotlinJvmTarget
            freeCompilerArgs = kotlinFreeCompilerArgs
        }
    }

    withType<Test> {
        useJUnitPlatform()
    }

    jacocoTestCoverageVerification {
        violationRules {
            rule {
                limit {
                    minimum = BigDecimal.valueOf(0.2)
                }
            }
        }
    }

    jacocoTestReport {
        reports {
            xml.isEnabled = true
            html.isEnabled = false
        }
    }

    val integrationTest = getByName(integrationTestName) {
        mustRunAfter(getByName("test"))
    }

    check {
        dependsOn(integrationTest, jacocoTestCoverageVerification, jacocoTestReport)
    }
}

sonarqube {
    properties {
        property("sonar.projectKey", "com.codemonkeyfactory.test.logging")
        property("sonar.projectName", "CodeMonkeyFactory Logging Test")
        property("sonar.coverage.exclusions", "**/*Constants.kt")
    }
}

publishing {
    publications {
        create<MavenPublication>("loggingTest") {
            artifactId = "logging-test"
            from(components["java"])
            pom {
                name.set("Logging Test")
                description.set("Logging Test Support Library")
                url.set("https://github.com/codemonkeyfactory/test-suite")
                developers {
                    developer {
                        id.set("kiongku")
                        name.set("Wai Keung Yiu Man Lung")
                        email.set("kiongku@users.noreply.github.com")
                    }
                }
                scm {
                    url.set("https://github.com/codemonkeyfactory/test-suite")
                    connection.set("scm:git:git://github.com/codemonkeyfactory/test-suite.git")
                    developerConnection.set("scm:git:git@github.com:codemonkeyfactory/test-suite.git")
                }
            }
        }
    }
}