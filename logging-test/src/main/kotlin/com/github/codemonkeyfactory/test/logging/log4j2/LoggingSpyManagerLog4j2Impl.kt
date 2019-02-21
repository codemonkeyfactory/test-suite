package com.github.codemonkeyfactory.test.logging.log4j2

import com.github.codemonkeyfactory.test.logging.LoggingSpy
import com.github.codemonkeyfactory.test.logging.LoggingSpyManager
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.core.LogEvent
import org.apache.logging.log4j.core.LoggerContext

/**
 * LoggingSpyManager implementation for Log4j2 API.
 */
class LoggingSpyManagerLog4j2Impl : LoggingSpyManager<Level, LogEvent> {
    private val observableAppender = InMemoryObservableAppender("observable")
    private val loggingSpy = LoggingSpyLog4j2Impl(observableAppender)

    override fun setupLoggingSpy() {
        val loggerContext = getLoggerContext()
        val configuration = loggerContext.configuration
        observableAppender.start()
        configuration.addAppender(observableAppender)
        configuration.loggers.forEach { _, loggerConfig ->
            loggerConfig.appenders.forEach { appenderName, _ ->
                loggerConfig.removeAppender(appenderName)
            }
            loggerConfig.addAppender(observableAppender, null, null)
            loggerConfig.level = Level.ALL
        }
        val rootLoggerConfig = configuration.rootLogger
        rootLoggerConfig.appenders.forEach { appenderName, _ ->
            rootLoggerConfig.removeAppender(appenderName)
        }
        rootLoggerConfig.addAppender(observableAppender, null, null)
        rootLoggerConfig.level = Level.ALL
        loggerContext.updateLoggers()
    }

    override fun tearDownLoggingSpy() {
        // Call reconfigure to reset the configuration
        // The drawback to this method is that any programmatic changes done after loading from source are lost
        observableAppender.stop()
        getLoggerContext().reconfigure()
    }

    override fun getLoggingSpy(): LoggingSpy<Level, LogEvent> = loggingSpy

    /**
     * Get the default logger context.
     *
     * @return Logger context
     */
    private fun getLoggerContext(): LoggerContext = LogManager.getContext(false) as LoggerContext
}