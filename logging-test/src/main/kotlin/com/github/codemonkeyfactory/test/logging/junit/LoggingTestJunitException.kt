package com.github.codemonkeyfactory.test.logging.junit

import com.github.codemonkeyfactory.test.logging.LoggingTestException

/**
 * LoggingTestException for JUnit
 */
open class LoggingTestJunitException(
    message: String,
    cause: Throwable? = null
) : LoggingTestException(message, cause)