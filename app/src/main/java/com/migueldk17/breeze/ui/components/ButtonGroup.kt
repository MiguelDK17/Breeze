package com.migueldk17.breeze.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonGroupDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.ToggleButton
import androidx.compose.material3.ToggleButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.github.migueldk17.breezeicons.icons.BreezeIcon
import com.github.migueldk17.breezeicons.icons.BreezeIcons
import com.github.migueldk17.breezeicons.icons.BreezeIconsType
import kotlinx.collections.immutable.ImmutableList

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun BreezeButtonGroup(
    options: ImmutableList<String>,
    onChangeSelectedIndex: (Int) -> Unit,
    modifier: Modifier = Modifier,
    checkedIcons: ImmutableList<BreezeIconsType>? = null,
    unCheckedIcons: ImmutableList<BreezeIconsType>? = null,
    initialSelectedIcon: Int = 0
    ){
    var selectedIndex by remember { mutableIntStateOf(initialSelectedIcon) }

    Row(
        modifier.padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(ButtonGroupDefaults.ConnectedSpaceBetween),

    ) {
        val modifiers = listOf(Modifier.weight(1f), Modifier.weight(1.5f), Modifier.weight(1f))

        options.forEachIndexed { index, label ->
            ToggleButton(
                checked = selectedIndex == index,
                onCheckedChange = {
                    selectedIndex = index
                    onChangeSelectedIndex(selectedIndex)
                },
                modifier = modifiers[index].semantics { role = Role.RadioButton },
                shapes =
                    when (index) {
                        0 -> ButtonGroupDefaults.connectedLeadingButtonShapes()
                        options.lastIndex -> ButtonGroupDefaults.connectedTrailingButtonShapes()
                        else -> ButtonGroupDefaults.connectedMiddleButtonShapes()
                    },
            ) {
                val icon = when {
                    checkedIcons == null -> null
                    selectedIndex == index -> checkedIcons[index]
                    else -> unCheckedIcons?.get(index)

                }
                icon?.let {
                    BreezeIcon(
                        icon,
                        contentDescription = label,
                    )

                }
                Spacer(Modifier.size(ToggleButtonDefaults.IconSpacing))
                DescriptionText(label)
            }
        }
    }
}