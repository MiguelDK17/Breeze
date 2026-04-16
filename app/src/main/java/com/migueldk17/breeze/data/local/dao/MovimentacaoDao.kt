package com.migueldk17.breeze.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.migueldk17.breeze.data.local.entity.MovimentacaoEntity
import com.migueldk17.breeze.dto.CategoryTotalDto
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal

@Dao
interface MovimentacaoDao {
    //Insere um objeto saldo na tabela
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovimentacao(movimentacaoEntity: MovimentacaoEntity)

    //Pega a soma de todos os valores em saldo_table
    @Query("SELECT SUM(valor) FROM movimentacao_entity")
    fun getSaldoTotal(): Flow<BigDecimal?>

    //Busca o primeiro registro da tabela saldo_table
    @Query("SELECT * FROM movimentacao_entity ORDER BY data DESC")
    fun getAllMovimentacoes(): Flow<List<MovimentacaoEntity>> //Retorna null se a tabela estiver vazia

    @Query("SELECT * FROM movimentacao_entity WHERE data LIKE :mesAno || '%' AND tipo = 0")
    fun getReceitasDoMes(mesAno: String): Flow<List<MovimentacaoEntity>>

    @Query("SELECT * FROM movimentacao_entity WHERE data LIKE:mesAno || '%'")
    fun getMovimentacoesDoMes(mesAno: String): Flow<List<MovimentacaoEntity>>

    @Query("SELECT * FROM movimentacao_entity WHERE data LIKE :diaMesAno || '%'")
    fun getMovimentacoesDoDia(diaMesAno: String): Flow<List<MovimentacaoEntity>>

    @Query("""
        SELECT c.category, SUM(m.valor) as totalAmount
        FROM movimentacao_entity m
        INNER JOIN conta_table c ON m.contaId = c.id
        WHERE m.data LIKE :mesAno || '%'
        GROUP BY c.category
        ORDER BY totalAmount DESC
    """
    )

    fun getCategoryTotalsByMonth(
        mesAno: String
    ): Flow<List<CategoryTotalDto>>

    @Delete
    fun apagaMovimentacao(movimentacaoEntity: MovimentacaoEntity)
}