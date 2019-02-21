package com.github.codemonkeyfactory.test.logging

import java.time.Instant

/**
 * A snapshot of log data captured when logging is performed.
 *
 * @param Level Logger level type
 * @param LogEvent Original logger event type
 * @property event Logging Event captured
 * @property level Logging Level captured
 * @property loggerName Name of logger responsible for this log
 * @property time Log capture time
 * @property message Human readable log message
 * @property exception Associated log exception if available
 */
data class CapturedLog<Level, LogEvent>(
    val event: LogEvent,
    val level: Level,
    val loggerName: String,
    val time: Instant,
    val message: String? = null,
    val exception: Throwable? = null
)