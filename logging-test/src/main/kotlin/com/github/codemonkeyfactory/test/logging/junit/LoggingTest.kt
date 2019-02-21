package com.github.codemonkeyfactory.test.logging.junit

import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

/**
 * Annotation test marker.
 */
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.FUNCTION)
@MustBeDocumented
@Test
@ExtendWith(LoggingTestExtension::class)
@Tag("logging-test")
annotation class LoggingTest {
}