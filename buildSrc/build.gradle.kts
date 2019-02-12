plugins {
    val kotlinDslVersion = "1.1.3"
    id("org.gradle.kotlin.kotlin-dsl") version kotlinDslVersion
}

repositories {
    mavenLocal()
    mavenCentral()
    jcenter()
}