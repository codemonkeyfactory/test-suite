package com.codemonkeyfactory.test.logging

/**
 * Base exception for all exceptions of logging test.
 *
 * @constructor Create instance of [LoggingTestException]
 * @param message Exception message
 * @param cause Exception root cause exception
 */
open class LoggingTestException(
    message: String,
    cause: Throwable? = null
) : Exception(message, cause)