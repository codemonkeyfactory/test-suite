package com.github.codemonkeyfactory.test.logging.log4j2

import com.github.codemonkeyfactory.test.logging.CapturedLog
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.core.Appender
import org.apache.logging.log4j.core.Appender.ELEMENT_TYPE
import org.apache.logging.log4j.core.Core
import org.apache.logging.log4j.core.LogEvent
import org.apache.logging.log4j.core.appender.AbstractAppender
import org.apache.logging.log4j.core.config.plugins.Plugin
import java.time.Instant

/**
 * Log4j2 [appender][Appender] that stores logs in an in-memory list.
 *
 * **Warning:** This appender is meant only to be used for testing
 */
@Plugin(
    name = "InMemoryObservableAppender",
    category = Core.CATEGORY_NAME,
    elementType = ELEMENT_TYPE
)
class InMemoryObservableAppender(
    name: String
) : AbstractAppender(name, null, null) {
    var isEnabled = false
        private set
    val capturedLogs: MutableList<CapturedLog<Level, LogEvent>> = mutableListOf()
    override fun append(event: LogEvent?) {
        if (isEnabled && event != null) {
            capturedLogs.add(
                CapturedLog(
                    event = event,
                    level = event.level,
                    loggerName = event.loggerName,
                    time = Instant.ofEpochSecond(event.instant.epochSecond, event.instant.nanoOfSecond.toLong()),
                    message = event.message.formattedMessage,
                    exception = event.thrown
                )
            )
        }
    }

    fun enable() {
        isEnabled = true
    }

    fun disable() {
        isEnabled = false
    }

    fun clear() {
        capturedLogs.clear()
    }
}