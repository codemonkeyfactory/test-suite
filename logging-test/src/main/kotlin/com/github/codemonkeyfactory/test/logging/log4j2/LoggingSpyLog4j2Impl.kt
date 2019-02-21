package com.github.codemonkeyfactory.test.logging.log4j2

import com.github.codemonkeyfactory.test.logging.CapturedLog
import com.github.codemonkeyfactory.test.logging.LoggingSpy
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.core.LogEvent

/**
 * [LoggingSpy] implementation for Log4j2.
 */
class LoggingSpyLog4j2Impl(
    private val appender: InMemoryObservableAppender
) : LoggingSpy<Level, LogEvent> {
    override fun getLogs(): List<CapturedLog<Level, LogEvent>> = appender.capturedLogs

    override fun enable() {
        appender.enable()
    }

    override fun disable() {
        appender.disable()
    }

    override fun clear() {
        appender.clear()
    }
}