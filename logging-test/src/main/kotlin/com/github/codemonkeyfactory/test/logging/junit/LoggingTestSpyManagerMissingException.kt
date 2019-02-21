package com.github.codemonkeyfactory.test.logging.junit

/**
 * Exception thrown when expected annotation LoggingTestSpyManager is missing on test method annotated with
 * LoggingTest.
 */
class LoggingTestSpyManagerMissingException(
    message: String = "Expected annotation LoggingTestSpyManager is missing",
    cause: Throwable? = null
) : LoggingTestJunitException(message, cause)