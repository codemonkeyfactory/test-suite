package com.github.codemonkeyfactory.test.logging.junit

import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.ParameterContext

@ExtendWith(MockKExtension::class)
@DisplayName("LoggingTestSpyManagerParameterResolver Tests")
internal class LoggingTestSpyManagerParameterResolverTest {
    private lateinit var loggingTestSpyManagerParameterResolver: LoggingTestSpyManagerParameterResolver

    @BeforeEach
    fun beforeEach() {
        loggingTestSpyManagerParameterResolver = LoggingTestSpyManagerParameterResolver()
    }

    @Test
    fun `Given a method with parameter of LoggingSpy interface, When call supportsParameter, Then returns true`(
        @MockK extensionContext: ExtensionContext
    ) {
        val parameterContext = getParameterContext(TestCaseWithLoggingSpyParameter::class)

        val result = loggingTestSpyManagerParameterResolver.supportsParameter(parameterContext, extensionContext)
        assertThat(result).isTrue()
    }

    @Test
    fun `Given a method with parameter of LoggingSpy derived type, When call supportsParameter, Then returns true`(
        @MockK extensionContext: ExtensionContext
    ) {
        val parameterContext = getParameterContext(TestCaseWithLoggingSpyTestDerivedParameter::class)

        val result = loggingTestSpyManagerParameterResolver.supportsParameter(parameterContext, extensionContext)
        assertThat(result).isTrue()
    }

    @Test
    fun `Given a method with parameter of Aby type, When call supportsParameter, Then returns false`(
        @MockK extensionContext: ExtensionContext
    ) {
        val parameterContext = getParameterContext(TestCaseWithAnyParameter::class)

        val result = loggingTestSpyManagerParameterResolver.supportsParameter(parameterContext, extensionContext)
        assertThat(result).isFalse()
    }

    @Test
    fun `Given extension context has manager stored, When resolveParameter, Then return manager`(
        @MockK parameterContext: ParameterContext
    ) {
        val extensionContext =
            getExtensionContext(TestCaseWithLoggingTestAndLoggingTestSpyManagerAnnotatedMethod::class)
        val manager = LoggingSpyManagerTest()
        getStore(extensionContext).put(LOGGING_SPY_LIFECYCLE_MANAGER, manager)

        val result = loggingTestSpyManagerParameterResolver.resolveParameter(parameterContext, extensionContext)
        assertThat(result).isSameAs(manager.getLoggingSpy())
    }

    @Test
    fun `Given extension context has no manager stored, When resolveParameter, Then throw LoggingTestSpyManagerMissingException`(
        @MockK parameterContext: ParameterContext
    ) {
        val extensionContext =
            getExtensionContext(TestCaseWithLoggingTestAndLoggingTestSpyManagerAnnotatedMethod::class)
        assertThatExceptionOfType(LoggingTestSpyManagerMissingException::class.java)
            .isThrownBy { loggingTestSpyManagerParameterResolver.resolveParameter(parameterContext, extensionContext) }
    }
}