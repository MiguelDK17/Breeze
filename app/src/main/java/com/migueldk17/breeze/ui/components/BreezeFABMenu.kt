package com.migueldk17.breeze.ui.components

import android.util.Log
import android.content.ContentValues.TAG
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Label
import androidx.compose.material.icons.automirrored.filled.Message
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Contacts
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Snooze
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingActionButtonMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleFloatingActionButton
import androidx.compose.material3.ToggleFloatingActionButtonDefaults.animateIcon
import androidx.compose.material3.animateFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.semantics.CustomAccessibilityAction
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.customActions
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.unit.dp
import com.github.migueldk17.breezeicons.icons.BreezeIcon
import com.github.migueldk17.breezeicons.icons.BreezeIcons
import com.migueldk17.breeze.ui.theme.DeepSkyBlue
import com.migueldk17.breeze.ui.theme.SkyBlue

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun BreezeFABMenu(
    onChangeSelectedCategory: (String) -> Unit
){
    val listState = rememberLazyListState()
    val fabVisible by remember { derivedStateOf { listState.firstVisibleItemIndex == 0 } }
    val isDarkModeActive = isSystemInDarkTheme()

    Box() {
        val items =
            listOf(
                BreezeIcons.Linear.Money.MoneySend to "Contas",
                BreezeIcons.Linear.Money.MoneyRecive to "Receitas",
                BreezeIcons.Linear.Money.DollarSquare to "Comparativo"
            )

        var fabMenuExpanded by rememberSaveable { mutableStateOf(false) }

        BackHandler(fabMenuExpanded) { fabMenuExpanded = false }

        FloatingActionButtonMenu(
            modifier = Modifier.align(Alignment.BottomEnd),
            expanded = fabMenuExpanded,
            button = {
                ToggleFloatingActionButton(
                    modifier =
                        Modifier
                            .semantics {
                                traversalIndex = -1f
                                stateDescription = if (fabMenuExpanded) "Expanded" else "Collapsed"
                                contentDescription = "Toggle menu"
                            }
                            .animateFloatingActionButton(
                                visible = fabVisible || fabMenuExpanded,
                                alignment = Alignment.BottomEnd
                            ),
                    checked = fabMenuExpanded,
                    onCheckedChange = {
                        fabMenuExpanded = !fabMenuExpanded
                        Log.d(TAG, "BreezeFABMenu: $fabMenuExpanded")
                                      },
                    containerColor = {
                        if (isDarkModeActive) DeepSkyBlue else SkyBlue
                    }
                ) {
                    val imageVector by remember {
                        derivedStateOf {
                            if (checkedProgress > 0.5f) Icons.Filled.Close else Icons.Filled.Add
                        }
                    }
                    Icon(
                        painter = rememberVectorPainter(imageVector),
                        contentDescription = null,
                        modifier = Modifier
                            .animateIcon( {checkedProgress} ),

                    )
                }
            },
        ) {
            if (fabMenuExpanded) {
                Log.d(TAG, "BreezeFABMenu: tamanho da lista ${items.size}")
                items.forEachIndexed { i, item ->
                    FloatingActionButtonMenu(
                        modifier = Modifier.semantics {
                            isTraversalGroup = true

                            if (i == items.size - 1) {
                                customActions =
                                    listOf(
                                        CustomAccessibilityAction(
                                            label = "Close menu",
                                            action = {
                                                fabMenuExpanded = false
                                                true
                                            },
                                        )
                                    )
                            }
                        },
                        button = {
                            Button(
                                onClick = {
                                    onChangeSelectedCategory(item.second)
                                    Log.d(TAG, "BreezeFABMenu: você clicou em: ${item.second}")
                                    fabMenuExpanded = false
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Transparent
                                    ),
                                border = BorderStroke(width = 0.4.dp, color = Color(0xFFBDBDBD))
                            ) {
                                BreezeIcon(
                                    item.first,
                                    contentDescription = item.second
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                DescriptionText(item.second)

                            }

                        },
                        expanded = fabMenuExpanded,
                        content = {

                        }
                    )
                }
            }
        }
    }
}