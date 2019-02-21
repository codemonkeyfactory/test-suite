package com.github.codemonkeyfactory.test.logging.junit

import com.github.codemonkeyfactory.test.logging.LoggingSpyManager
import org.junit.jupiter.api.extension.AfterTestExecutionCallback
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.platform.commons.support.AnnotationSupport

/**
 * Junit5 extension for logging test.
 *
 * @constructor Create a new instance of this extension.
 */
class LoggingTestExtension : BeforeTestExecutionCallback, AfterTestExecutionCallback {
    /**
     * Callback that is invoked immediately before each test is executed.
     *
     * @param extensionContext The current extension context, never null
     */
    override fun beforeTestExecution(extensionContext: ExtensionContext) {
        val loggingTestSpyLifeCycleManager = getLoggingSpyLifeCycleManager(extensionContext)
        getStore(extensionContext).put(LOGGING_SPY_LIFECYCLE_MANAGER, loggingTestSpyLifeCycleManager)
        loggingTestSpyLifeCycleManager.setupLoggingSpy()
    }

    /**
     * Callback that is invoked immediately after each test has been executed.
     *
     * @param extensionContext The current extension, never null
     */
    override fun afterTestExecution(extensionContext: ExtensionContext) {
        val manager = getStore(extensionContext).get(LOGGING_SPY_LIFECYCLE_MANAGER) as? LoggingSpyManager<*, *>
            ?: throw LoggingTestSpyManagerMissingException()
        manager.tearDownLoggingSpy()
    }

    /**
     * Get the [logging spy manager][LoggingSpyManager] from the Junit5 extension context.
     *
     * @param extensionContext Extension context in use by [logging test extension][LoggingTestExtension], never null
     * @return The [logging spy manager][LoggingSpyManager] in the extension context store
     */
    private fun getLoggingSpyLifeCycleManager(extensionContext: ExtensionContext): LoggingSpyManager<*, *> {
        val testMethod = extensionContext.requiredTestMethod
        AnnotationSupport.findAnnotation(testMethod, LoggingTest::class.java)
            .orElseThrow {
                LoggingTestMissingException()
            }
        val loggingTestSpyManager =
            AnnotationSupport.findAnnotation(testMethod, LoggingTestSpyManager::class.java)
                .orElseThrow {
                    LoggingTestSpyManagerMissingException()
                }
        return loggingTestSpyManager.value.java.getDeclaredConstructor().newInstance()
    }
}