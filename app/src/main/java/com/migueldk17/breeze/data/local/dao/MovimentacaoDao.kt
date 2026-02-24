package com.migueldk17.breeze.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.migueldk17.breeze.data.local.entity.MovimentacaoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovimentacaoDao {
    //Insere um objeto saldo na tabela
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserirReceita(movimentacaoEntity: MovimentacaoEntity)

    //Pega a soma de todos os valores em saldo_table
    @Query("SELECT SUM(valor) FROM movimentacao_entity")
    fun getSaldoTotal(): Flow<Double?>

    //Busca o primeiro registro da tabela saldo_table
    @Query("SELECT * FROM movimentacao_entity ORDER BY data DESC")
    fun getAllMovimentacoes(): Flow<List<MovimentacaoEntity>> //Retorna null se a tabela estiver vazia

    @Query("SELECT * FROM movimentacao_entity WHERE data LIKE :mesAno || '%' AND tipo = 0")
    fun getReceitasDoMes(mesAno: String): Flow<List<MovimentacaoEntity>>

    @Delete
    fun apagaMovimentacao(movimentacaoEntity: MovimentacaoEntity)
}