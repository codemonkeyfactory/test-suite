package com.github.codemonkeyfactory.test.logging.junit

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.engine.execution.ExtensionValuesStore
import org.junit.jupiter.engine.execution.NamespaceAwareStore
import java.lang.reflect.AnnotatedElement
import java.lang.reflect.Method
import java.util.*
import kotlin.reflect.KClass
import kotlin.reflect.full.declaredFunctions
import kotlin.reflect.jvm.javaMethod

internal fun getExtensionContext(testCase: KClass<*>): ExtensionContext {
    val method = testCase.declaredFunctions.firstOrNull {
        it.name == "method"
    }?.javaMethod
    return object : ExtensionContext {
        private val store = ExtensionValuesStore(null)

        override fun getElement(): Optional<AnnotatedElement>? = null

        override fun getParent(): Optional<ExtensionContext>? = null

        override fun getTestInstance(): Optional<Any> = Optional.empty()

        override fun getTestClass(): Optional<Class<*>>? = null

        override fun getDisplayName(): String? = null

        override fun getUniqueId(): String? = null

        override fun getRoot(): ExtensionContext = this

        override fun getExecutionException(): Optional<Throwable> = Optional.empty()

        override fun getTestMethod(): Optional<Method> = Optional.ofNullable(method)

        override fun getConfigurationParameter(key: String?): Optional<String> = Optional.empty()

        override fun getTestInstanceLifecycle(): Optional<TestInstance.Lifecycle> = Optional.empty()

        override fun getTags(): MutableSet<String>? = null

        override fun publishReportEntry(map: MutableMap<String, String>?) {
            // no-op
        }

        override fun getStore(namespace: ExtensionContext.Namespace?): ExtensionContext.Store =
            NamespaceAwareStore(store, namespace)
    }
}

internal class TestCaseWithoutMethod

internal class TestCaseWithMethod {
    fun method() {}
}

internal class TestCaseWithLoggingTestAnnotatedMethod {
    @Disabled // dont treat as a real test
    @LoggingTest
    fun method() {
    }
}

internal class TestCaseWithLoggingTestAndLoggingTestSpyManagerAnnotatedMethod {
    @Disabled // dont treat as a real test
    @LoggingTest
    @LoggingTestSpyManager(LoggingSpyManagerTest::class)
    fun method() {
    }
}