package com.github.codemonkeyfactory.test.logging.junit

/**
 * Exception thrown when expected annotation LoggingTest is missing on test method.
 */
class LoggingTestMissingException(
    message: String = "Expected annotation LoggingTest is missing",
    cause: Throwable? = null
) : LoggingTestJunitException(message, cause)