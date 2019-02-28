val upperCaseLetters = "\\p{Upper}".toRegex()
val kebabCaseLetters = "-\\p{Lower}".toRegex()

/**
 * Returns the string in kebab case.
 *
 * "toKebabCase" -> "to-kebab-case"
 */
fun String.toKebabCase() =
    replace(upperCaseLetters) { "-${it.value.toLowerCase()}" }

/**
 * Returns the string in camel case.
 *
 * "to-camel-case" -> "toCamelCase"
 */
fun String.toCamelCase() =
    replace(kebabCaseLetters) { it.value.removePrefix("-").toUpperCase() }

/**
 * Returns true if string contains 'snapshot' keyword. Ignores case sensitivity.
 */
fun String.isSnapshot() =
        contains("snapshot", true)

/**
 * Returns true if string contains 'release' keyword. Ignores case sensitivity.
 */
fun String.isRelease() =
        contains("release", true)