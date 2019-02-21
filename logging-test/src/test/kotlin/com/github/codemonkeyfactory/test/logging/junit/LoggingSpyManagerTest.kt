package com.github.codemonkeyfactory.test.logging.junit

import com.github.codemonkeyfactory.test.logging.LoggingSpy
import com.github.codemonkeyfactory.test.logging.LoggingSpyManager

internal class LoggingSpyManagerTest : LoggingSpyManager<Any, Any> {
    var isSetupCalled = false
    var isTearDownCalled = false
    private val loggingSpy = LoggingSpyTest()

    override fun setupLoggingSpy() {
        isSetupCalled = true
    }

    override fun tearDownLoggingSpy() {
        isTearDownCalled = true
    }

    override fun getLoggingSpy(): LoggingSpy<Any, Any> = loggingSpy
}