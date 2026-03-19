package com.migueldk17.breeze.domain

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.github.migueldk17.breezeicons.icons.BreezeIconsType
import com.migueldk17.breeze.converters.toColor
import com.migueldk17.breeze.enums.TipoComparacao
import com.migueldk17.breeze.enums.TipoMovimentacao
import com.migueldk17.breeze.ui.theme.RedError
import java.math.BigDecimal
import java.time.LocalDate

data class MovimentacaoDomain(

    val id: Long = 0L,

    val valor: BigDecimal,

    val descricao: String,

    val date: LocalDate,

    val icon: String,

    val tipo: TipoMovimentacao,

    val colorIcon: Color = returnColorIcon(tipo), //Valor padrão remete ao IconGreen do BreezeIcons
    val colorCard: Color = returnColorCard(tipo), // Valor padrão remete ao SoftBlue do BreezeIcons


    val contaId: Long? = null
)

private fun returnColorIcon(tipoMovimentacao: TipoMovimentacao): Color {
    return when (tipoMovimentacao) {
        TipoMovimentacao.ENTRADA -> (-9573033).toColor()
        TipoMovimentacao.SAIDA -> RedError
    }
}

private fun returnColorCard(tipoMovimentacao: TipoMovimentacao): Color {
  return when(tipoMovimentacao) {
      TipoMovimentacao.ENTRADA -> (-1838339).toColor()
      TipoMovimentacao.SAIDA -> Color(0xFFF3A7B1)

  }
}
