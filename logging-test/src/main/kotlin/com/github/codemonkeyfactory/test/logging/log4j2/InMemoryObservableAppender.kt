package com.github.codemonkeyfactory.test.logging.log4j2

import com.github.codemonkeyfactory.test.logging.CapturedLog
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.core.Appender.ELEMENT_TYPE
import org.apache.logging.log4j.core.Core
import org.apache.logging.log4j.core.LogEvent
import org.apache.logging.log4j.core.appender.AbstractAppender
import org.apache.logging.log4j.core.config.plugins.Plugin
import java.time.Instant

/**
 * Log4j2 Appender that stores logs in an in-memory list.
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
    /**
     * Flag to track if appender is enabled
     */
    var isEnabled = false
        private set

    /**
     * Internal list of captured logs
     */
    val capturedLogs: MutableList<CapturedLog<Level, LogEvent>> = mutableListOf()

    /**
     * Add the event to captured logs.
     *
     * @param event The log event
     */
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

    /**
     * Enable the logging spy and start recording logs.
     */
    fun enable() {
        isEnabled = true
    }

    /**
     * Disable the logging spy and stop recording logs.
     */
    fun disable() {
        isEnabled = false
    }

    /**
     * Clear the logs recorded up to now by the spy.
     */
    fun clear() {
        capturedLogs.clear()
    }
}