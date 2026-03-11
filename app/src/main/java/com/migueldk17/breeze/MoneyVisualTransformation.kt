package com.migueldk17.breeze

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import java.util.Locale
import java.math.BigDecimal
import java.math.RoundingMode

class MoneyVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val cleanText = text.text.filter { it.isLetterOrDigit() }
        val value = cleanText.toBigDecimalOrNull() ?: BigDecimal.ZERO

        //Formata o valor como moeda (R$ 0,00)
        val formattedValue = String.format(Locale.getDefault(),"R$: %.2f", value.divide(BigDecimal(100), 2, RoundingMode.HALF_EVEN))
        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                return formattedValue.length
            }

            override fun transformedToOriginal(offset: Int): Int {
                return cleanText.length
            }

        }
        return TransformedText(AnnotatedString(formattedValue), offsetMapping)
    }
}