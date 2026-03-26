package com.migueldk17.breeze.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.migueldk17.breezeicons.icons.BreezeIcons
import com.migueldk17.breeze.enums.TipoMovimentacao
import java.math.BigDecimal

@Entity(tableName = "movimentacao_entity")
data class MovimentacaoEntity(
    //Chave primária do banco de dados
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    //Nome da coluna do SQLite
    @ColumnInfo(name = "valor")
    val valor: BigDecimal,

    @ColumnInfo(name = "descricao")
    val descricao: String,

    @ColumnInfo(name = "data")
    val data: String, //Está sendo salvo como LocalDate.toString()

    @ColumnInfo("tipo")
    val tipo: TipoMovimentacao,

    @ColumnInfo(name = "icon")
    val icon: String = returnIcon(tipo),

    val contaId: Long? = null
)

private fun returnIcon(tipo: TipoMovimentacao): String{
    val string = when(tipo){
        TipoMovimentacao.ENTRADA -> {
            "MONEY_RECEIVE_LINEAR"
        }
        TipoMovimentacao.SAIDA -> {
            "MONEY_SEND_LINEAR"
        }
    }
    return string
}
