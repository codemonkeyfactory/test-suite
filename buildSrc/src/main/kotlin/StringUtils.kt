val upperCaseLetters = "\\p{Upper}".toRegex()
val kebabCaseLetters = "-\\p{Lower}".toRegex()

fun String.toKebabCase() =
    replace(upperCaseLetters) { "-${it.value.toLowerCase()}" }

fun String.toCamelCase() =
    replace(kebabCaseLetters) { it.value.removePrefix("-").toUpperCase() }