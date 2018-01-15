package com.lightningkite.kotlin.text

@Deprecated("Use built-in functionality instead.", ReplaceWith(
        "toFloatOrNull()"
))
fun String.toFloatMaybe() = toFloatOrNull()

@Deprecated("Use built-in functionality instead.", ReplaceWith(
        "toDoubleOrNull()"
))
fun String.toDoubleMaybe() = toDoubleOrNull()

@Deprecated("Use built-in functionality instead.", ReplaceWith(
        "toIntOrNull()"
))
fun String.toIntMaybe() = toIntOrNull()

@Deprecated("Use built-in functionality instead.", ReplaceWith(
        "toLongOrNull()"
))
fun String.toLongMaybe() = toLongOrNull()


@Deprecated("Use built-in functionality instead.", ReplaceWith(
        "toFloatOrNull() ?: default"
))
fun String.toFloatMaybe(default: Float) = toFloatOrNull() ?: default

@Deprecated("Use built-in functionality instead.", ReplaceWith(
        "toDoubleOrNull() ?: default"
))
fun String.toDoubleMaybe(default: Double) = toDoubleOrNull() ?: default

@Deprecated("Use built-in functionality instead.", ReplaceWith(
        "toIntOrNull() ?: default"
))
fun String.toIntMaybe(default: Int) = toIntOrNull() ?: default

@Deprecated("Use built-in functionality instead.", ReplaceWith(
        "toLongOrNull() ?: default"
))
fun String.toLongMaybe(default: Long) = toLongOrNull() ?: default