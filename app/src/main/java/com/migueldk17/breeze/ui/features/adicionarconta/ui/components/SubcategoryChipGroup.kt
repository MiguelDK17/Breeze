package com.migueldk17.breeze.ui.features.adicionarconta.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SubcategoryChipGroup(
    modifier: Modifier = Modifier,
    selectedCategory: String,
    subCategoriesMap: Map<String, List<String>>,
    selectedSubcategory: String,
    onSubCategorySelected: (String) -> Unit
){
    //Pega o map de sub categorias enviado do Passo1 e seleciona apenas a lista correspondente a categoria selecionada
    val subCategories = subCategoriesMap[selectedCategory] ?: emptyList() //Caso não tenha sub categorias com base na categoria ele irá retornar uma lista vazia

    if (subCategories.isNotEmpty()) {
        Column(
            modifier = modifier
        ) {
            Text(
                "Escolha uma subcategoria",
                fontSize = 14.sp,
                fontWeight = FontWeight.W300,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            FlowRow(
            modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                //Pra cada item da lista um Chip é criado
                subCategories.forEach { subCategory ->
                    val isSelected = subCategory == selectedSubcategory //Verifica se a sub categoria selecionada é igual a sub categoria atual

                    Surface(
                        shape = RoundedCornerShape(50),
                        color = if (isSelected) MaterialTheme.colorScheme.primary else Color(0xFFE0E0E0),
                        modifier = Modifier
                            .clickable { onSubCategorySelected(subCategory) }
                    ) {
                        Text(
                            text = subCategory,
                            color = if (isSelected) Color.White else Color.Black,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}