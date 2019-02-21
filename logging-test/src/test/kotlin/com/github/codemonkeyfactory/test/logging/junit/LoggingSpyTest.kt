package com.github.codemonkeyfactory.test.logging.junit

import com.github.codemonkeyfactory.test.logging.CapturedLog
import com.github.codemonkeyfactory.test.logging.LoggingSpy

internal class LoggingSpyTest : LoggingSpy<Any, Any> {
    override fun getLogs(): List<CapturedLog<Any, Any>> = emptyList()

    override fun enable() {
        // no-op
    }

    override fun disable() {
        // no-op
    }

    override fun clear() {
        // no-op
    }
}