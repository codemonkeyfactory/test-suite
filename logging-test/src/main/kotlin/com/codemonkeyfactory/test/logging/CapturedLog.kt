package com.codemonkeyfactory.test.logging

import java.time.Instant

/**
 * A snapshot of log data captured when logging is performed.
 *
 * @param Level Logger level type
 * @param LogEvent Original logger event type
 */
data class CapturedLog<Level, LogEvent>(
    val event: LogEvent,
    val level: Level,
    val loggerName: String,
    val time: Instant,
    val message: String? = null,
    val exception: Throwable? = null
)