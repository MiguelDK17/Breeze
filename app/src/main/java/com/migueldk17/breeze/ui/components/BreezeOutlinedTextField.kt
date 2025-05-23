package com.migueldk17.breeze.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import com.migueldk17.breeze.ui.features.adicionarconta.ui.components.DescriptionText

@Composable
fun BreezeOutlinedTextField(
                            modifier: Modifier = Modifier,
                            text: String,
                            onValueChange: (String) -> Unit,
                            textLabel: String,
                            isError: Boolean = false,
                            keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
                            visualTransformation: VisualTransformation = VisualTransformation.None,

) {
    OutlinedTextField(
        text,
        onValueChange = onValueChange,
        modifier =  modifier,
        label = {
            DescriptionText(textLabel)
        },
        minLines = 1,
        keyboardOptions = keyboardOptions,
        isError = isError,
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color(0xFFF5F5F5),
            unfocusedContainerColor = Color(0xFFF5F5F5),
            unfocusedBorderColor = Color(0xFFF5F5F5)
        ),
        visualTransformation = visualTransformation
    )
}