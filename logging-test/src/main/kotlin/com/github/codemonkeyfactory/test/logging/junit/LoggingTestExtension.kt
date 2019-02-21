package com.github.codemonkeyfactory.test.logging.junit

import com.github.codemonkeyfactory.test.logging.LoggingSpyManager
import org.junit.jupiter.api.extension.AfterTestExecutionCallback
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.platform.commons.support.AnnotationSupport

class LoggingTestExtension : BeforeTestExecutionCallback, AfterTestExecutionCallback {
    override fun beforeTestExecution(extensionContext: ExtensionContext) {
        val loggingTestSpyLifeCycleManager = getLoggingSpyLifeCycleManager(extensionContext)
        getStore(extensionContext).put(LOGGING_SPY_LIFECYCLE_MANAGER, loggingTestSpyLifeCycleManager)
        loggingTestSpyLifeCycleManager.setupLoggingSpy()
    }

    override fun afterTestExecution(extensionContext: ExtensionContext) {
        val manager = getStore(extensionContext).get(LOGGING_SPY_LIFECYCLE_MANAGER) as? LoggingSpyManager<*, *>
            ?: throw LoggingTestSpyManagerMissingException()
        manager.tearDownLoggingSpy()
    }

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