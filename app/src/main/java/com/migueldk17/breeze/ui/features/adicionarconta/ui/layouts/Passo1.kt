package com.migueldk17.breeze.ui.features.adicionarconta.ui.layouts

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.migueldk17.breeze.NavGraph2
import com.migueldk17.breeze.ui.components.BreezeButton
import com.migueldk17.breeze.ui.components.BreezeDropdownMenu
import com.migueldk17.breeze.ui.components.InfoIconWithPopup
import com.migueldk17.breeze.ui.features.adicionarconta.ui.components.DescriptionText
import com.migueldk17.breeze.ui.features.adicionarconta.ui.components.PersonalizationCard
import com.migueldk17.breeze.ui.features.adicionarconta.ui.components.SubcategoryChipGroup
import com.migueldk17.breeze.ui.features.adicionarconta.viewmodels.AdicionarContaViewModel
import com.migueldk17.breeze.ui.theme.Blue
import com.migueldk17.breeze.ui.theme.BreezeTheme
import com.migueldk17.breeze.ui.theme.NavyBlue
import com.migueldk17.breeze.ui.theme.PastelLightBlue
import java.nio.file.WatchEvent

@Composable
fun Passo1(navController: NavController = rememberNavController(), viewModel: AdicionarContaViewModel = hiltViewModel()){
    var text by remember{
        mutableStateOf("")
    }
    val categories = listOf(
        "Alimentação",
        "Transporte",
        "Educação",
        "Moradia",
        "Lazer",
        "Saúde",
        "Trabalho/Negócios",
        "Pets",
        "Pessoais",
        "Outros")
    var selectedCategory by remember { mutableStateOf("Selecione uma categoria") }
    var selectedSubCategory by remember { mutableStateOf("") }
    val categorySubcategories = mapOf(
        "Alimentação" to listOf("Supermercado", "Restaurante", "Lanches", "Delivery"),
        "Transporte" to listOf("Combustível", "Uber/99", "Ônibus/Transporte público", "Estacionamento"),
        "Educação" to listOf("Escola", "Faculdade", "Cursos online", "Material escolar"),
        "Moradia" to listOf("Aluguel", "Condomínio", "Água", "Energia", "Internet"),
        "Lazer" to listOf("Cinema", "Viagens", "Assinaturas(Netflix, Spotify...)", "Jogos"),
        "Saúde" to listOf("Plano de saúde", "Farmácia", "Consulta médica", "Exames"),
        "Trabalho/Negócios" to listOf("Ferramentas de trabalho", "Marketing", "Transporte a trabalho", "Assinaturas por trabalho"),
        "Pets" to listOf("Ração", "Veterinário", "Higiene", "Brinquedos"),
        "Pessoais" to listOf("Roupas", "Cabelo/Beleza", "Presentes", "Academia"),
        "Outros" to listOf("Doações", "Imprevistos", "Dívidas antigas", "Sem subcategoria")
    )



    Column(
        modifier = Modifier
            .padding(25.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DescriptionText("Parece que o card de sua nova conta está vazio:")
        Spacer(modifier = Modifier.size(25.dp))
        //Card que evolui conforme o usuario vai adicionando informações
        PersonalizationCard()
        Spacer(modifier = Modifier.size(26.dp))

        DescriptionText("Vamos começar adicionando um nome !")
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 26.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            //TextField responsável por adicionar um nome a conta
            OutlinedTextField(
                text,
                onValueChange = { value ->
                    text = value
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Adicionar nome")
                },
                minLines = 1,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                isError = !textoCorreto(text),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFF5F5F5),
                    unfocusedContainerColor = Color(0xFFF5F5F5),
                    unfocusedBorderColor = Color(0xFFF5F5F5)
                )
            )
            if (!textoCorreto(text)) {
                Text(
                    "O nome da conta deve ter entre dois caracteres e 20 caracteres",
                    color = Color.Red,
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 12.sp
                )
            }
        }

        BreezeDropdownMenu(
            modifier = Modifier.padding(vertical = 10.dp),
            categoryName = "Insira uma categoria(Opcional)",
            categories = categories,
            selectedCategory = selectedCategory,
            onCategorySelected = { selectedCategory = it }
        )
        if (selectedCategory != "Selecione uma categoria") {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                DescriptionText("Agora, adicione uma sub-categoria!")
                InfoIconWithPopup("Se não tiver uma opção ideal, pode usar 'Outros' e 'Sem subcategoria'!")
            }
        }
        SubcategoryChipGroup(
            modifier = Modifier.padding(vertical = 16.dp),
            selectedCategory = selectedCategory,
            subCategoriesMap = categorySubcategories,
            selectedSubcategory = selectedSubCategory,
            onSubCategorySelected = { selectedSubCategory = it}
        )
        //Botão para avançar de tela
        BreezeButton(
            modifier = Modifier.padding(vertical = 30.dp),
            text = "Avançar", onClick = {
                viewModel.setNomeConta(text)
                viewModel.setCategoria(selectedCategory)
                viewModel.setSubcategoria(selectedSubCategory)
                navController.navigate(NavGraph2.Passo2.route)

            },
            enabled = isBreezeButtonEnabled(text, selectedCategory, selectedSubCategory))
    }
    Log.d(TAG, "Passo1Preview: ${
        isBreezeButtonEnabled(
            text,
            selectedCategory,
            selectedSubCategory
        )
    }")
}

private fun textoCorreto(text: String): Boolean {
    return  text.length <= 20
}

private fun isBreezeButtonEnabled(text: String, categorySelected: String, subcategorySelected: String): Boolean {
    val verificacaoText = text.isNotEmpty() && textoCorreto(text)
    val verificacaoCategory = categorySelected.isNotEmpty() && categorySelected != "Selecione uma categoria"
    val verificacaoSubCategory = verificacaoCategory == true && subcategorySelected.isNotEmpty()

    return verificacaoText && verificacaoCategory && verificacaoSubCategory
}

