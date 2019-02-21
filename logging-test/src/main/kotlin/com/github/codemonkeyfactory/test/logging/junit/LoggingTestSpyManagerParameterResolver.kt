package com.github.codemonkeyfactory.test.logging.junit

import com.github.codemonkeyfactory.test.logging.LoggingSpy
import com.github.codemonkeyfactory.test.logging.LoggingSpyManager
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.ParameterContext
import org.junit.jupiter.api.extension.ParameterResolver

/**
 * Junit5 ParameterResolver for LoggingTest.
 */
class LoggingTestSpyManagerParameterResolver : ParameterResolver {
    /**
     * Determine if the parameter is compatible with [LoggingSpy].
     *
     * @param parameterContext context for the parameter for which an argument should be resolved; never null
     * @param extensionContext extension context for the Executable about to be invoked; never null
     * @return _true_ if this resolver can resolve an argument for the parameter
     */
    override fun supportsParameter(parameterContext: ParameterContext, extensionContext: ExtensionContext): Boolean {
        if (LoggingSpy::class.java.isAssignableFrom(parameterContext.parameter.type)) {
            return true
        }
        return false
    }

    /**
     * Resolve an appropriate [LoggingSpy] if _supportsParameter_ returns _true_.
     * It requires a proper [LoggingSpyManager] through the [LoggingTestSpyManager] annotation.
     *
     * @param parameterContext context for the parameter for which an argument should be resolved; never null
     * @param extensionContext extension context for the Executable about to be invoked; never null
     * @return the resolved [LoggingSpy]
     * @throws LoggingTestSpyManagerMissingException if a [LoggingSpyManager] is not configured
     */
    override fun resolveParameter(parameterContext: ParameterContext, extensionContext: ExtensionContext): Any {
        val manager = getStore(extensionContext).get(LOGGING_SPY_LIFECYCLE_MANAGER) as? LoggingSpyManager<*, *>
            ?: throw LoggingTestSpyManagerMissingException()
        return manager.getLoggingSpy()
    }
}