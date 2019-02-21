package com.github.codemonkeyfactory.test.logging

import com.github.codemonkeyfactory.test.logging.junit.LoggingTest
import com.github.codemonkeyfactory.test.logging.junit.LoggingTestSpyManager
import com.github.codemonkeyfactory.test.logging.log4j2.LoggingSpyManagerLog4j2Impl
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.core.LogEvent
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName

@DisplayName("LogTest Integration Tests")
internal class LogTestIntegrationTest {
    @LoggingTest
    @LoggingTestSpyManager(LoggingSpyManagerLog4j2Impl::class)
    fun `Given logging spy is enabled, When logger info is called, Then logging spy contains the new log`(loggingSpy: LoggingSpy<Level, LogEvent>) {
        val infoMessage = "info test"
        loggingSpy.enable()
        val dummy = LogDummy()
        dummy.info(infoMessage)
        loggingSpy.disable()
        val capturedLogs = loggingSpy.getLogs()
        assertThat(capturedLogs).hasSize(1)
        assertThat(capturedLogs[0].message).isEqualTo(infoMessage)
    }

    @LoggingTest
    @LoggingTestSpyManager(LoggingSpyManagerLog4j2Impl::class)
    fun `Given logging spy is enabled, When logger error is called, Then logging spy contains the new log`(loggingSpy: LoggingSpy<Level, LogEvent>) {
        val errorMessage = "error test"
        val errorException = LogDummyException()
        loggingSpy.enable()
        val dummy = LogDummy()
        dummy.error(errorMessage, errorException)
        loggingSpy.disable()
        val capturedLogs = loggingSpy.getLogs()
        assertThat(capturedLogs).hasSize(1)
        assertThat(capturedLogs[0].message).isEqualTo(errorMessage)
        assertThat(capturedLogs[0].exception).isEqualTo(errorException)
    }
}