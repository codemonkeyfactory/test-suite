package com.codemonkeyfactory.test.logging.junit

import com.codemonkeyfactory.test.logging.LoggingSpy
import com.codemonkeyfactory.test.logging.LoggingSpyManager
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.reflect.KClass

/**
 * Companion annotation for [LoggingTest] in order to provide the [LoggingSpyManager] used to manage
 * the [LoggingSpy].
 */
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.FUNCTION)
@MustBeDocumented
@ExtendWith(LoggingTestSpyManagerParameterResolver::class)
@Tag("logging-test")
annotation class LoggingTestSpyManager(
    val value: KClass<out LoggingSpyManager<*, *>>
)