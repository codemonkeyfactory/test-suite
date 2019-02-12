package com.codemonkeyfactory.test.logging.junit

import com.codemonkeyfactory.test.logging.LoggingSpy
import org.junit.jupiter.api.extension.ParameterContext
import java.lang.reflect.Parameter
import java.util.*
import kotlin.reflect.KClass
import kotlin.reflect.full.declaredFunctions
import kotlin.reflect.jvm.javaMethod

internal fun getParameterContext(testCase: KClass<*>): ParameterContext {
    val parameter = testCase.declaredFunctions.firstOrNull {
        it.name == "method"
    }?.javaMethod?.parameters?.firstOrNull()
    return object : ParameterContext {
        override fun <A : Annotation?> findRepeatableAnnotations(annotationType: Class<A>?): MutableList<A> =
            mutableListOf()

        override fun <A : Annotation?> findAnnotation(annotationType: Class<A>?): Optional<A> =
            Optional.empty()

        override fun getParameter(): Parameter = parameter!! // parameter should not null

        override fun getIndex(): Int = 0

        override fun getTarget(): Optional<Any> = Optional.empty()

        override fun isAnnotated(annotationType: Class<out Annotation>?): Boolean = false
    }
}

internal class TestCaseWithAnyParameter {
    fun method(@Suppress("UNUSED_PARAMETER") parameter: Any) {}
}

internal class TestCaseWithLoggingSpyParameter {
    fun method(@Suppress("UNUSED_PARAMETER")parameter: LoggingSpy<*, *>) {}
}

internal class TestCaseWithLoggingSpyTestDerivedParameter {
    fun method(@Suppress("UNUSED_PARAMETER")parameter: LoggingSpyTest) {}
}