package com.lightningkite.kotlin.anko.observable

import android.text.InputType
import android.widget.EditText
import com.lightningkite.kotlin.anko.NumericalString
import com.lightningkite.kotlin.anko.lifecycle
import com.lightningkite.kotlin.anko.textChanger
import com.lightningkite.kotlin.observable.property.MutableObservableProperty
import com.lightningkite.kotlin.observable.property.bind
import java.text.NumberFormat

/**
 * Binds this [EditText] two way to the bond.
 * When the user edits this, the value of the bond will change.
 * When the value of the bond changes, the number here will be updated.
 */
@Deprecated("Numerical moved out")
fun EditText.bindDoubleAutoComma(bond: MutableObservableProperty<Double>, format: NumberFormat = NumberFormat.getNumberInstance()) {
    inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL

    var iSet = false
    textChanger {
        val resultString = format.format(it.after.filter {
            it.isDigit()
                    || it == NumericalString.decimalChar
                    || it == NumericalString.negativeChar
        }.toDoubleOrNull() ?: 0.0)
        val insertionPoint = NumericalString.transformPosition(it.after, resultString, it.insertionPoint + it.replacement.length).coerceIn(0, resultString.length)

//        println("write")
        iSet = true
        bond.value = format.parse(resultString).toDouble()

        resultString to insertionPoint..insertionPoint
    }
    lifecycle.bind(bond) {
        if (iSet) {
//            println("ignored")
            iSet = false
        } else {
//            println("read")
            this.setText(format.format(bond.value))
        }
    }
}

@Deprecated("Numerical moved out")
fun EditText.bindNullableDoubleAutoComma(bond: MutableObservableProperty<Double?>, format: NumberFormat = NumberFormat.getNumberInstance()) {
    inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL

    var iSet = false
    textChanger {
        val maybeNumber = it.after.filter {
            it.isDigit()
                    || it == NumericalString.decimalChar
                    || it == NumericalString.negativeChar
        }.toDoubleOrNull()
        if (maybeNumber != null) {
            val resultString = format.format(maybeNumber)
            val insertionPoint = NumericalString.transformPosition(it.after, resultString, it.insertionPoint + it.replacement.length).coerceIn(0, resultString.length)

//        println("write")
            iSet = true
            bond.value = format.parse(resultString).toDouble()

            resultString to insertionPoint..insertionPoint
        } else {
            iSet = true
            bond.value = null
            "" to 0..0
        }
    }
    lifecycle.bind(bond) {
        if (iSet) {
//            println("ignored")
            iSet = false
        } else {
//            println("read")
            val value = bond.value
            if (value != null) {
                this.setText(format.format(value))
            } else {
                this.text = null
            }
        }
    }
}