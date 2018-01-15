package com.lightningkite.kotlin.anko

import android.widget.EditText
import java.text.DecimalFormat
import java.text.NumberFormat

/**
 * Deals with numerical strings
 * Created by jivie on 7/5/16.
 */
@Deprecated("Doesn't belong in general library.")
object NumericalString {

    val localeSymbols = (DecimalFormat.getInstance() as? DecimalFormat)?.decimalFormatSymbols
    val separatorChar = localeSymbols?.groupingSeparator ?: ','
    val decimalChar = localeSymbols?.decimalSeparator ?: '.'
    val negativeChar = localeSymbols?.minusSign ?: '-'

    const val POSITION_DECIMAL = Int.MAX_VALUE
    const val POSITION_NEGATIVE = Int.MAX_VALUE - 1
    const val POSITION_UNKNOWN = Int.MAX_VALUE - 2
    const val POSITION_SEPARATOR_FOLLOWING = Int.MIN_VALUE

    data class NumericalPosition(
            var status: Status,
            var precedingOrCurrentExponent: Int
    ) {
        enum class Status {
            Digit,
            Decimal,
            Negative,
            Unknown,
            SeparatorFollowing,
            End
        }
    }

    fun transformPosition(before: String, after: String, position: Int): Int {
        val numPos = numericalPosition(before, position)
        val charPos = charPosition(after, numPos)
        return charPos
    }

    /**
     * Finds the numerical position (exponent) of the character at [position].
     *
     * @return The order of magnitude of the character - for example, 2 would indicate that the
     * character * 10^2 is the value this particular character contributes.
     */
    fun numericalPosition(input: String, position: Int): NumericalPosition {
        if (position >= input.length) {
            val decimalPosition = input.indexOfFirst { it == decimalChar }
            return if (decimalPosition != -1) {
                val postDecimalDigitCount = input.substring(decimalPosition).count(Char::isDigit)
                return NumericalPosition(NumericalPosition.Status.Digit, postDecimalDigitCount - 1)
            } else {
                return NumericalPosition(NumericalPosition.Status.Decimal, 0)
            }
        }
        var currPos = 0
        var digitCount = 0
        var decimalBefore = Int.MAX_VALUE
        var myDigit = 0
        val result = NumericalPosition(NumericalPosition.Status.Digit, 0)
        for (char in input) {
            when (char) {
                in '0'..'9' -> {
                    if (currPos == position) {
                        result.status = NumericalPosition.Status.Digit
                    }
                    digitCount++
                }
                negativeChar -> {
                    if (currPos == position) {
                        result.status = NumericalPosition.Status.Negative
                    }
                }
                separatorChar -> {
                    if (currPos == position) {
                        result.status = NumericalPosition.Status.SeparatorFollowing
                    }
                }
                decimalChar -> {
                    if (currPos == position) {
                        result.status = NumericalPosition.Status.Decimal
                    }
                    decimalBefore = digitCount
                }
                else -> {

                }
            }
            if (currPos == position) {
                myDigit = digitCount - 1
            }

            currPos++
        }
        result.precedingOrCurrentExponent = (digitCount - myDigit) - (digitCount - decimalBefore.coerceIn(0, digitCount)) - 1
        return result
    }

    fun charPosition(input: String, numericalPosition: NumericalPosition): Int {

        var currPos = 0
        var digitCount = 0
        var decimalBefore = Int.MAX_VALUE
        for (char in input) {
            when (char) {
                in '0'..'9' -> {
                    digitCount++
                }
                decimalChar -> {
                    decimalBefore = digitCount
                }
            }

            currPos++
        }

        var afterCharPos = 0
        var myDigit = 0
        var charPos = 0
        for (char in input) {
            if (char.isDigit()) {
                if ((digitCount - myDigit) - (digitCount - decimalBefore.coerceIn(0, digitCount)) - 1 == numericalPosition.precedingOrCurrentExponent) {
                    afterCharPos = charPos
                }
                myDigit++
            }
            charPos++
        }

        when (numericalPosition.status) {
            NumericalPosition.Status.Digit -> {
                return afterCharPos
            }
            NumericalPosition.Status.Decimal -> {
                val pos = input.indexOf(decimalChar, afterCharPos)
                return if (pos == -1) input.length else pos
            }
            NumericalPosition.Status.Negative -> {
                val pos = input.indexOf(negativeChar, afterCharPos)
                return if (pos == -1) input.indexOfFirst(Char::isDigit).coerceAtLeast(0) else pos
            }
            NumericalPosition.Status.SeparatorFollowing -> {
                val pos = input.indexOf(separatorChar, afterCharPos)
                return if (pos == -1) afterCharPos else pos
            }
            NumericalPosition.Status.Unknown -> {
                return 0
            }
            NumericalPosition.Status.End -> {
                return input.length
            }
        }
    }
}

@Deprecated("Doesn't belong in general library.")
fun EditText.autoComma(format: NumberFormat) {
    textChanger {
        val resultString = format.format(it.after.filter {
            it.isDigit()
                    || it == NumericalString.decimalChar
                    || it == NumericalString.negativeChar
        }.toDoubleOrNull() ?: 0.0)
        val insertionPoint = NumericalString.transformPosition(it.after, resultString, it.insertionPoint + it.replacement.length).coerceIn(0, resultString.length)
        resultString to insertionPoint..insertionPoint
    }
}