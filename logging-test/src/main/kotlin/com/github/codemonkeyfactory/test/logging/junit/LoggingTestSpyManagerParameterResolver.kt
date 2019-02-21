package com.github.codemonkeyfactory.test.logging.junit

import com.github.codemonkeyfactory.test.logging.LoggingSpy
import com.github.codemonkeyfactory.test.logging.LoggingSpyManager
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.ParameterContext
import org.junit.jupiter.api.extension.ParameterResolver

class LoggingTestSpyManagerParameterResolver : ParameterResolver {
    override fun supportsParameter(parameterContext: ParameterContext, extensionContext: ExtensionContext): Boolean {
        if (LoggingSpy::class.java.isAssignableFrom(parameterContext.parameter.type)) {
            return true
        }
        return false
    }

    override fun resolveParameter(parameterContext: ParameterContext, extensionContext: ExtensionContext): Any {
        val manager = getStore(extensionContext).get(LOGGING_SPY_LIFECYCLE_MANAGER) as? LoggingSpyManager<*, *>
            ?: throw LoggingTestSpyManagerMissingException()
        return manager.getLoggingSpy()
    }
}