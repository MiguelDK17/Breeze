package com.migueldk17.breeze.ui.components


import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import com.migueldk17.breeze.ui.theme.NavyPetrol

@Composable
fun BreezeOutlinedTextField(
                            modifier: Modifier = Modifier, //Modificador padrão
                            text: String, //Texto do campo
                            onValueChange: (String) -> Unit, //Função que é chamada quando o texto é alterado
                            textLabel: String, //Texto do label
                            isError: Boolean = false, //Booleano de erro
                            keyboardOptions: KeyboardOptions = KeyboardOptions.Default, //Opções de teclado padrão
                            visualTransformation: VisualTransformation = VisualTransformation.None, //Transformação visual padrão

) {
    OutlinedTextField(
        text, //Texto do campo
        onValueChange = onValueChange, //Função que é chamada quando o texto é alterado
        modifier =  modifier,
        label = {
            DescriptionText(textLabel)
        }, //Texto do label
        minLines = 1, //Número mínimo de linhas
        keyboardOptions = keyboardOptions, //Opções de teclado
        isError = isError, //Booleano de erro
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = if(!isSystemInDarkTheme()) Color(0xFFF5F5F5) else NavyPetrol, //Cor do campo quando está focado
            unfocusedContainerColor = if(!isSystemInDarkTheme()) Color(0xFFF5F5F5) else NavyPetrol, //Cor do campo quando não está focado
            unfocusedBorderColor = if(!isSystemInDarkTheme()) Color(0xFFF5F5F5) else NavyPetrol //Cor da borda do campo quando não está focado
        ),
        visualTransformation = visualTransformation //Transformação visual do texto
    )
}