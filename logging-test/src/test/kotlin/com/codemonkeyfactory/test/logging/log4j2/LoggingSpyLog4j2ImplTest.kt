package com.codemonkeyfactory.test.logging.log4j2

import com.codemonkeyfactory.test.logging.CapturedLog
import io.mockk.clearMocks
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.core.LogEvent
import org.apache.logging.log4j.core.impl.Log4jLogEvent
import org.apache.logging.log4j.core.time.MutableInstant
import org.apache.logging.log4j.message.SimpleMessage
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Condition
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.function.Predicate

@ExtendWith(MockKExtension::class)
@DisplayName("LoggingSpyLog4j2Impl Tests")
internal class LoggingSpyLog4j2ImplTest {
    @MockK
    private lateinit var appender: InMemoryObservableAppender
    private lateinit var spyLog: LoggingSpyLog4j2Impl

    @BeforeEach
    fun beforeEach() {
        clearMocks(appender)
        spyLog = LoggingSpyLog4j2Impl(appender)
    }

    @Test
    fun `When call getLogs, Then return all logs`() {
        mockLogReturn()
        val capturedLogs = spyLog.getLogs()

        verify(exactly = 1) {
            appender.capturedLogs
        }
        confirmVerified(appender)

        assertThat(capturedLogs).isNotEmpty
    }

    @Test
    fun `When call getLogs with specified level, Then returns logs with specified level`() {
        mockLogReturn()
        val capturedLogs = spyLog.getLogs(Level.INFO)

        verify(exactly = 1) {
            appender.capturedLogs
        }
        confirmVerified(appender)

        assertThat(capturedLogs).hasSize(2)
        val infoLevel = Condition<CapturedLog<Level, LogEvent>>(
            Predicate {
                it.level == Level.INFO
            },
            "captured log has info level"
        )
        assertThat(capturedLogs).have(infoLevel)
    }

    @Test
    fun `When call getLogs with specified level and logger name, Then returns logs with specified level and logger name`() {
        mockLogReturn()
        val capturedLogs = spyLog.getLogs(Level.INFO, "LoggerInfoEventTest")

        verify(exactly = 1) {
            appender.capturedLogs
        }
        confirmVerified(appender)

        assertThat(capturedLogs).hasSize(1)
        val infoLevel = Condition<CapturedLog<Level, LogEvent>>(
            Predicate {
                it.level == Level.INFO
            },
            "info level"
        )
        val loggerName = Condition<CapturedLog<Level, LogEvent>>(
            Predicate {
                it.loggerName == "LoggerInfoEventTest"
            },
            "'LoggerInfoEventTest' name"
        )
        assertThat(capturedLogs).have(infoLevel).have(loggerName)
    }

    @Test
    fun `When call enable, Then call appender's enable`() {
        every {
            appender.enable()
        } returns Unit
        spyLog.enable()
        verify(exactly = 1) {
            appender.enable()
        }
        confirmVerified(appender)
    }

    @Test
    fun `When call disable, Then call appender's disable`() {
        every {
            appender.disable()
        } returns Unit
        spyLog.disable()
        verify(exactly = 1) {
            appender.disable()
        }
        confirmVerified(appender)
    }

    @Test
    fun `When call clear, Then call appender's clear`() {
        every {
            appender.clear()
        } returns Unit
        spyLog.clear()
        verify(exactly = 1) {
            appender.clear()
        }
        confirmVerified(appender)
    }

    private fun mockLogReturn() {
        val time = java.time.Instant.now()
        val eventTime = MutableInstant()
        eventTime.initFromEpochSecond(time.epochSecond, time.nano)
        val exception = RuntimeException("test exception")
        val firstLogEvent = Log4jLogEvent.newBuilder()
            .setLevel(Level.ERROR)
            .setInstant(eventTime)
            .setLoggerName("LoggerErrorEventTest")
            .setMessage(SimpleMessage("Logger Error Message Test"))
            .setThrown(exception)
            .build()
        @Suppress("UNCHECKED_CAST")
        val firstLog = CapturedLog(
            event = firstLogEvent,
            level = Level.ERROR,
            loggerName = "LoggerErrorEventTest",
            time = time,
            message = "Logger Error Message Test",
            exception = exception
        ) as CapturedLog<Level, LogEvent>
        val secondLogEvent = Log4jLogEvent.newBuilder()
            .setLevel(Level.INFO)
            .setInstant(eventTime)
            .setLoggerName("LoggerInfoEventTest")
            .setMessage(SimpleMessage("Logger Info Message Test 1"))
            .build()
        @Suppress("UNCHECKED_CAST")
        val secondLog = CapturedLog(
            event = secondLogEvent,
            level = Level.INFO,
            loggerName = "LoggerInfoEventTest",
            time = time,
            message = "Logger Info Message Test 1"
        ) as CapturedLog<Level, LogEvent>
        val thirdLogEvent = Log4jLogEvent.newBuilder()
            .setLevel(Level.INFO)
            .setInstant(eventTime)
            .setLoggerName("LoggerInfoEventTest2")
            .setMessage(SimpleMessage("Logger Info Message Test 2"))
            .build()
        @Suppress("UNCHECKED_CAST")
        val thirdLog = CapturedLog(
            event = thirdLogEvent,
            level = Level.INFO,
            loggerName = "LoggerInfoEventTest2",
            time = time,
            message = "Logger Info Message Test 2"
        ) as CapturedLog<Level, LogEvent>
        every {
            spyLog.getLogs()
        } returns listOf(firstLog, secondLog, thirdLog)
    }
}