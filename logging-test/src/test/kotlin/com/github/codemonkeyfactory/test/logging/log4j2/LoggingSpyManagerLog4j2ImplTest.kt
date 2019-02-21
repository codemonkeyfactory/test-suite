package com.github.codemonkeyfactory.test.logging.log4j2

import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.core.LoggerContext
import org.apache.logging.log4j.core.appender.ConsoleAppender
import org.apache.logging.log4j.core.config.AppenderRef
import org.apache.logging.log4j.core.config.LoggerConfig
import org.apache.logging.log4j.core.layout.PatternLayout
import org.apache.logging.log4j.status.StatusLogger
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Condition
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.util.function.Predicate

@DisplayName("LoggingSpyManagerLog4j2Impl Tests")
internal class LoggingSpyManagerLog4j2ImplTest {
    private lateinit var manager: LoggingSpyManagerLog4j2Impl

    @BeforeEach
    fun beforeEach() {
        manager = LoggingSpyManagerLog4j2Impl()
    }

    @AfterEach
    fun afterEach() {
        getLoggerContext().reconfigure() // force blind reset
    }

    @Test
    fun `Given LoggerContext only has root logger, When setupLoggingSpy, Then root LoggerContext contains InMemoryObservableAppender`() {
        val loggerContext = getLoggerContext()
        manager.setupLoggingSpy()
        val configuration = loggerContext.configuration
        configuration.loggers.forEach {
            assertThat(it.value)
                .`as`("Each logger has observable appender")
                .has(hasObservableAppender)
        }
        assertThat(configuration.rootLogger)
            .`as`("Root logger has observable appender")
            .has(hasObservableAppender)
    }

    @Test
    fun `Given LoggerContext has existing loggers, When setupLoggingSpy, Then all LoggerContexts contains InMemoryObservableAppender`() {
        val loggerContext = getLoggerContext()
        val configuration = loggerContext.configuration
        val layout = PatternLayout.newBuilder().build()
        val appender = ConsoleAppender.createDefaultAppenderForLayout(layout)
        configuration.addAppender(appender)
        val appenderRefs: Array<AppenderRef> = arrayOf()
        val loggerConfig =
            LoggerConfig.createLogger(
                false,
                Level.INFO,
                "test",
                "true",
                appenderRefs,
                null,
                configuration,
                null
            )
        loggerConfig.addAppender(appender, null, null)
        configuration.addLogger(
            "test",
            loggerConfig
        )
        loggerContext.updateLoggers()
        manager.setupLoggingSpy()
        configuration.loggers.forEach {
            assertThat(it.value)
                .`as`("Each logger has observable appender")
                .has(hasObservableAppender)
        }
        assertThat(configuration.rootLogger)
            .`as`("Root logger has observable appender")
            .has(hasObservableAppender)
    }

    @Test
    fun `When tearDownLoggingSpy, The LoggerContext does not contain InMemoryObservableAppender`() {
        val loggerContext = getLoggerContext()
        manager.setupLoggingSpy()
        manager.tearDownLoggingSpy()
        val configuration = loggerContext.configuration
        configuration.loggers.forEach {
            assertThat(it.value)
                .`as`("Each logger does not have observable appender")
                .doesNotHave(hasObservableAppender)
        }
        assertThat(configuration.rootLogger)
            .`as`("Root logger does not have observable appender")
            .doesNotHave(hasObservableAppender)
    }

    @Test
    fun `When getLoggingSpy, Then return the Log4j2 spy`() {
        assertThat(manager.getLoggingSpy())
            .isNotNull
            .isExactlyInstanceOf(LoggingSpyLog4j2Impl::class.java)
    }

    private fun getLoggerContext(): LoggerContext {
        val originalLevel = StatusLogger.getLogger().level
        StatusLogger.getLogger().level = Level.OFF
        val loggerContext = LogManager.getContext(false) as LoggerContext
        StatusLogger.getLogger().level = originalLevel
        return loggerContext
    }

    private val hasObservableAppender = Condition<LoggerConfig>(
        Predicate { loggerConfig ->
            loggerConfig.appenders.any { appenderEntry ->
                InMemoryObservableAppender::class.isInstance(appenderEntry.value)
            }
        },
        "has observable appender"
    )
}