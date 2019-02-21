package com.github.codemonkeyfactory.test.logging.log4j2

import org.apache.logging.log4j.Level
import org.apache.logging.log4j.core.impl.Log4jLogEvent
import org.apache.logging.log4j.core.time.MutableInstant
import org.apache.logging.log4j.message.SimpleMessage
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("InMemoryObservableAppender Tests")
internal class InMemoryObservableAppenderTest {
    companion object {
        private const val APPENDER_NAME = "observable"
    }

    private lateinit var appender: InMemoryObservableAppender

    @BeforeEach
    fun beforeEach() {
        appender = InMemoryObservableAppender(APPENDER_NAME)
    }

    @Test
    fun `Given appender name provided is 'observable', When a new instance is created, Then appender name is 'observable'`() {
        assertThat(appender.name).isEqualTo(APPENDER_NAME)
    }

    @Test
    fun `When a new instance is created, Then isEnabled returns false`() {
        assertThat(appender.isEnabled).isFalse()
    }

    @Test
    fun `When appender is enabled, Then isEnabled returns true`() {
        appender.enable()
        assertThat(appender.isEnabled).isTrue()
    }

    @Test
    fun `Given appender is enabled, When appender is disabled, Then isEnabled returns false`() {
        appender.enable()
        appender.disable()
        assertThat(appender.isEnabled).isFalse()
    }

    @Test
    fun `Given appender is disabled, When append new log, Then logs is empty`() {
        appender.disable() // making sure its disabled
        val loggerLevel = Level.ERROR
        val loggerName = "LoggerErrorEventTest"
        val loggerMessage = "Logger Error Message Test"
        val time = java.time.Instant.now()
        val eventTime = MutableInstant()
        eventTime.initFromEpochSecond(time.epochSecond, time.nano)
        val exception = RuntimeException("test exception")
        val logEvent = Log4jLogEvent.newBuilder()
            .setLevel(loggerLevel)
            .setInstant(eventTime)
            .setLoggerName(loggerName)
            .setMessage(SimpleMessage(loggerMessage))
            .setThrown(exception)
            .build()
        appender.append(logEvent)
        assertThat(appender.capturedLogs).isEmpty()
    }

    @Test
    fun `Given appender is enabled, When append null, Then logs is empty`() {
       appender.enable()
        appender.append(null)
        assertThat(appender.capturedLogs).isEmpty()
    }

    @Test
    fun `Given appender is enabled, When append new log, Then logs contain the new log`() {
        appender.enable()
        val loggerLevel = Level.ERROR
        val loggerName = "LoggerErrorEventTest"
        val loggerMessage = "Logger Error Message Test"
        val time = java.time.Instant.now()
        val eventTime = MutableInstant()
        eventTime.initFromEpochSecond(time.epochSecond, time.nano)
        val exception = RuntimeException("test exception")
        val logEvent = Log4jLogEvent.newBuilder()
            .setLevel(loggerLevel)
            .setInstant(eventTime)
            .setLoggerName(loggerName)
            .setMessage(SimpleMessage(loggerMessage))
            .setThrown(exception)
            .build()
        appender.append(logEvent)
        assertThat(appender.capturedLogs).hasSize(1)
        val capturedLog = appender.capturedLogs[0]
        assertThat(capturedLog.event).isSameAs(logEvent)
        assertThat(capturedLog.level).isEqualTo(loggerLevel)
        assertThat(capturedLog.loggerName).isEqualTo(loggerName)
        assertThat(capturedLog.time).isEqualTo(time)
        assertThat(capturedLog.message).isEqualTo(loggerMessage)
        assertThat(capturedLog.exception).isSameAs(exception)
    }

    @Test
    fun `Given appender contains logs, When clear, Then logs is empty`() {
        appender.enable()
        val loggerLevel = Level.ERROR
        val loggerName = "LoggerErrorEventTest"
        val loggerMessage = "Logger Error Message Test"
        val time = java.time.Instant.now()
        val eventTime = MutableInstant()
        eventTime.initFromEpochSecond(time.epochSecond, time.nano)
        val exception = RuntimeException("test exception")
        val logEvent = Log4jLogEvent.newBuilder()
            .setLevel(loggerLevel)
            .setInstant(eventTime)
            .setLoggerName(loggerName)
            .setMessage(SimpleMessage(loggerMessage))
            .setThrown(exception)
            .build()
        appender.append(logEvent)
        appender.clear()
        assertThat(appender.capturedLogs).isEmpty()
    }
}
