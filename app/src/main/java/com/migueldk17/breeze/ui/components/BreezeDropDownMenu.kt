package com.migueldk17.breeze.ui.components


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.migueldk17.breeze.ui.theme.NavyPetrol

@Composable
fun BreezeDropdownMenu(
    modifier: Modifier = Modifier,
    categoryName: String,
    categories: List<String>,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit,
    showDescriptionText: Boolean = true
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        if (showDescriptionText) {
            Text(
                text = categoryName,
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.W300
            )

            Spacer(modifier = Modifier.height(4.dp))
        }

        Box(
            modifier = modifier
                .clip(RoundedCornerShape(12.dp))
                .background(if(!isSystemInDarkTheme()) Color(0xFFF5F5F5) else NavyPetrol)
                .clickable { expanded = true }
                .padding(horizontal = 16.dp, vertical = 12.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = selectedCategory,
                    fontSize = 16.sp,
                    color = if(!isSystemInDarkTheme()) Color.Black else Color(0xFFF5F5F5))
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Open menu"
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                categories.forEach { category ->
                    DropdownMenuItem(
                        text = {
                            Row {
                                Text(text = category)
                                if (category == selectedCategory) {
                                    Text("✔️", modifier = Modifier.padding(start = 4.dp))
                                }
                            }
                        },
                        onClick = {
                            onCategorySelected(category)
                            expanded = false
                        }
                    )
                }
            }

        }
    }
}