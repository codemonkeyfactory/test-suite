package com.github.codemonkeyfactory.test.logging

import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

internal class LogDummy {
    fun info(message: String) {
        logger.info(message)
    }

    fun error(message: String, exception: Exception) {
        logger.error(message, exception)
    }
}

internal class LogDummyException : RuntimeException()