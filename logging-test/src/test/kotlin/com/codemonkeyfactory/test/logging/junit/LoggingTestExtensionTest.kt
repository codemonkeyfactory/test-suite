package com.codemonkeyfactory.test.logging.junit

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.platform.commons.util.PreconditionViolationException

@DisplayName("LoggingTestExtension Tests")
internal class LoggingTestExtensionTest {
    private lateinit var loggingTestExtension: LoggingTestExtension

    @BeforeEach
    fun beforeEach() {
        loggingTestExtension = LoggingTestExtension()
    }

    @Test
    fun `Given missing method, When call beforeTestExecution, Then throw PreconditionViolationException`() {
        val extensionContext = getExtensionContext(TestCaseWithoutMethod::class)
        assertThatExceptionOfType(PreconditionViolationException::class.java)
            .isThrownBy { loggingTestExtension.beforeTestExecution(extensionContext) }
    }

    @Test
    fun `Given method without LoggingTest annotation, When call beforeTestExecution, Then throw LoggingTestMissingException`() {
        val extensionContext = getExtensionContext(TestCaseWithMethod::class)
        assertThatExceptionOfType(LoggingTestMissingException::class.java)
            .isThrownBy { loggingTestExtension.beforeTestExecution(extensionContext) }
    }

    @Test
    fun `Given method with LoggingTest annotation but without LoggingTestSpyManager annotation, When call beforeTestExecution, Then throw LoggingTestSpyManagerMissingException`() {
        val extensionContext = getExtensionContext(TestCaseWithLoggingTestAnnotatedMethod::class)
        assertThatExceptionOfType(LoggingTestSpyManagerMissingException::class.java)
            .isThrownBy { loggingTestExtension.beforeTestExecution(extensionContext) }
    }

    @Test
    fun `Given method with LoggingTest and LoggingTestSpyManager annotations, When call beforeTestExecution, Then no exception is thrown and store contains LoggingSpyLifeCycleManagerTest with setup called`() {
        val extensionContext =
            getExtensionContext(TestCaseWithLoggingTestAndLoggingTestSpyManagerAnnotatedMethod::class)
        assertThatCode { loggingTestExtension.beforeTestExecution(extensionContext) }
            .doesNotThrowAnyException()
        val store = getStore(extensionContext)
        val manager = store.get(LOGGING_SPY_LIFECYCLE_MANAGER) as LoggingSpyManagerTest
        assertThat(manager.isSetupCalled).isTrue()
    }

    @Test
    fun `Given successful beforeTestExecution call, When call afterTestExecution, Then no exception is thrown and LoggingSpyLifeCycleManagerTest in store has teardown called`() {
        val extensionContext =
            getExtensionContext(TestCaseWithLoggingTestAndLoggingTestSpyManagerAnnotatedMethod::class)
        loggingTestExtension.beforeTestExecution(extensionContext)
        assertThatCode { loggingTestExtension.afterTestExecution(extensionContext) }
            .doesNotThrowAnyException()
        val store = getStore(extensionContext)
        val manager = store.get(LOGGING_SPY_LIFECYCLE_MANAGER) as LoggingSpyManagerTest
        assertThat(manager.isTearDownCalled).isTrue()
    }

    @Test
    fun `Given misconfigured extension with no manager stored, When call afterTestExecution, Then throw LoggingTestSpyManagerMissingException`() {
        val extensionContext =
                getExtensionContext(TestCaseWithLoggingTestAndLoggingTestSpyManagerAnnotatedMethod::class)
        assertThatExceptionOfType(LoggingTestSpyManagerMissingException::class.java)
            .isThrownBy { loggingTestExtension.afterTestExecution(extensionContext) }
    }
}