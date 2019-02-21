package com.github.codemonkeyfactory.test.logging.junit

import org.junit.jupiter.api.extension.ExtensionContext

/**
 * Get the store from the Junit5 extension context specific for the logging test extension.
 *
 * @param context Extension context in use by the logging test extension, never null
 * @return The store in use by the logging test extension in this extension context
 */
internal fun getStore(context: ExtensionContext) =
    context.getStore(
        ExtensionContext.Namespace.create(
            LoggingTest::class,
            LoggingTestSpyManager::class,
            context.requiredTestMethod
        )
    )