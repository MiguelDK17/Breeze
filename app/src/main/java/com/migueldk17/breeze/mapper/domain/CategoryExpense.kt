package com.migueldk17.breeze.mapper.domain

import com.migueldk17.breeze.domain.CategoryExpense
import com.migueldk17.breeze.ui.features.historico.ui.comparativo.model.CategoryUiModel
import com.migueldk17.breeze.ui.utils.CategoryColorProvider
import com.migueldk17.breeze.ui.utils.CategoryIconProvider

fun CategoryExpense.toUi(): CategoryUiModel {
    val icon = CategoryIconProvider.getIcon(category)
    val colorCard = CategoryColorProvider.returnCardColor(category)
    val colorIcon = CategoryColorProvider.returnIconColor(category)
    val progressBrush = CategoryColorProvider.returnBrush(category)

    return CategoryUiModel(
        category = category,
        totalAmount = totalAmount,
        percentage = percentage,
        icon = icon,
        progressBrush = progressBrush,
        colorCard = colorCard,
        colorIcon = colorIcon
    )
}