package com.github.codemonkeyfactory.test.logging

/**
 * Base exception for all exceptions of logging test.
 *
 * @constructor Create instance of LoggingTestException
 */
open class LoggingTestException(
    message: String,
    cause: Throwable? = null
) : Exception(message, cause)