package com.github.codemonkeyfactory.test.logging.junit

import org.junit.jupiter.api.extension.ExtensionContext

internal fun getStore(context: ExtensionContext) =
    context.getStore(
        ExtensionContext.Namespace.create(
            LoggingTest::class,
            LoggingTestSpyManager::class,
            context.requiredTestMethod
        )
    )