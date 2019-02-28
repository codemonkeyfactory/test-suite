plugins {
    val springDmVersion = "1.0.6.RELEASE"
    val kotlinDslVersion = "1.1.3"

    id("io.spring.dependency-management") version springDmVersion
    id("org.gradle.kotlin.kotlin-dsl") version kotlinDslVersion
}

repositories {
    mavenLocal()
    mavenCentral()
    jcenter()
}

val junit5Version = "5.3.2"
val assertJVersion = "3.11.1"

dependencyManagement {
    imports {
        mavenBom("org.junit:junit-bom:$junit5Version")
    }
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
    testImplementation("org.assertj:assertj-core:$assertJVersion")
}

tasks {
    test {
        useJUnitPlatform()
    }
}
