package com.codemonkeyfactory.test.logging.junit

import com.codemonkeyfactory.test.logging.LoggingSpy
import com.codemonkeyfactory.test.logging.LoggingSpyManager

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