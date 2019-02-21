package com.github.codemonkeyfactory.test.logging

/**
 * Test spy for observing captured logs.
 *
 * @param Level Logger level type
 * @param LogEvent Original logger event type
 */
interface LoggingSpy<Level, LogEvent> {
    /**
     * Get logs captured by the spy.
     *
     * @return Show all captured logs by the spy
     */
    fun getLogs(): List<CapturedLog<Level, LogEvent>>

    /**
     * Get logs captured by the spy with the specified level.
     *
     * @param level Logger level type of captured logs, never null
     * @return Show all captured logs by the spy of the specified level
     */
    fun getLogs(level: Level): List<CapturedLog<Level, LogEvent>> {
        return getLogs().asSequence()
            .filter { it.level == level }
            .toList()
    }

    /**
     * Get logs captured by the spy with the specified level and logger name.
     *
     * @param level Logger level type of captured logs, never null
     * @param loggerName Logger name of captured logs, never null
     * @return Show all captured logs by the spy of the specified level and specified logger name
     */
    fun getLogs(level: Level, loggerName: String): List<CapturedLog<Level, LogEvent>> {
        return getLogs().asSequence()
            .filter { it.level == level && it.loggerName == loggerName }
            .toList()
    }

    /**
     * Enable the spy.
     */
    fun enable()

    /**
     * Disable the spy.
     */
    fun disable()

    /**
     * Clear all the captured logs by the spy.
     */
    fun clear()
}