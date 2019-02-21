package com.github.codemonkeyfactory.test.logging

/**
 * Manager for LoggingSpy.
 *
 * @param Level Logger level type
 * @param LogEvent Original logger event type
 */
interface LoggingSpyManager<Level, LogEvent> {
    /**
     * Setup the logging spy.
     */
    fun setupLoggingSpy()

    /**
     * Tear down the logging spy.
     */
    fun tearDownLoggingSpy()

    /**
     * Get the logging spy managed by this manager instance
     */
    fun getLoggingSpy(): LoggingSpy<Level, LogEvent>
}