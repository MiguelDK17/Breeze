package com.migueldk17.breeze.ui.features.adicionarconta.ui.layouts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.migueldk17.breeze.NavGraph2
import com.migueldk17.breeze.ui.components.BreezeButton
import com.migueldk17.breeze.ui.components.BreezeDropdownMenu
import com.migueldk17.breeze.ui.components.BreezeOutlinedTextField
import com.migueldk17.breeze.ui.components.InfoIconWithPopup
import com.migueldk17.breeze.ui.components.DescriptionText
import com.migueldk17.breeze.ui.features.adicionarconta.ui.components.PersonalizationCard
import com.migueldk17.breeze.ui.features.adicionarconta.ui.components.SubcategoryChipGroup
import com.migueldk17.breeze.ui.features.adicionarconta.viewmodels.AdicionarContaViewModel

@Composable
fun Passo1(
    navToPasso2: () -> Unit,
    viewModel: AdicionarContaViewModel = hiltViewModel()){
    var text by remember{
        mutableStateOf("")
    }
    //Lista de strings das categorias disponíveis para escolha do usuário para a conta
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
    //Variavel que armazena a categoria selecionada
    var selectedCategory by remember { mutableStateOf("Selecione uma categoria") }
    //Variavel que armazena a sub categoria selecionada
    var selectedSubCategory by remember { mutableStateOf("") }
    //Map de sub categoria baseado nas categorias estabelecidas
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
            BreezeOutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                text = text,
                onValueChange = { text = it},
                textLabel = "Adicionar nome",
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                isError = !textoCorreto(text)

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
                navToPasso2()

            },
            enabled = isBreezeButtonEnabled(text, selectedCategory, selectedSubCategory))
    }
}

private fun textoCorreto(text: String): Boolean {
    return  text.length <= 25
}

private fun isBreezeButtonEnabled(text: String, categorySelected: String, subcategorySelected: String): Boolean {
    val verificacaoText = text.isNotEmpty() && textoCorreto(text)
    val verificacaoCategory = categorySelected.isNotEmpty() && categorySelected != "Selecione uma categoria"
    val verificacaoSubCategory = verificacaoCategory == true && subcategorySelected.isNotEmpty()

    return verificacaoText && verificacaoCategory && verificacaoSubCategory
}

