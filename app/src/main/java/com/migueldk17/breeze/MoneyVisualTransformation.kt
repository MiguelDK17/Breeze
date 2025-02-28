package com.migueldk17.breeze

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import java.util.Locale

class MoneyVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val cleanText = text.text.filter { it.isLetterOrDigit() }
        val value = cleanText.toDoubleOrNull() ?: 0.0

        //Formata o valor como moeda (R$ 0,00)
        val formattedValue = String.format(Locale.getDefault(),"R$: %.2f", value / 100)
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