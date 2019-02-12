package com.codemonkeyfactory.test.logging.junit

import com.codemonkeyfactory.test.logging.CapturedLog
import com.codemonkeyfactory.test.logging.LoggingSpy

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