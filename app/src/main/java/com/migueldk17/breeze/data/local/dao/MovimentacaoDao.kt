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
    @Query("SELECT SUM(valor) FROM movimentacao")
    fun getSaldoTotal(): Flow<BigDecimal?>

    //Busca o primeiro registro da tabela saldo_table
    @Query("SELECT * FROM movimentacao ORDER BY date DESC")
    fun getAllMovimentacoes(): Flow<List<MovimentacaoEntity>> //Retorna null se a tabela estiver vazia

    @Query("SELECT * FROM movimentacao WHERE date LIKE :mesAno || '%' AND tipo = 0")
    fun getReceitasDoMes(mesAno: String): Flow<List<MovimentacaoEntity>>

    @Query("SELECT * FROM movimentacao WHERE date LIKE :mesAno || '%'")
    fun getMovimentacoesDoMes(mesAno: String): Flow<List<MovimentacaoEntity>>

    @Query("SELECT * FROM movimentacao WHERE date LIKE :diaMesAno || '%'")
    fun getMovimentacoesDoDia(diaMesAno: String): Flow<List<MovimentacaoEntity>>

    @Query("""
        SELECT c.category, SUM(ABS(m.valor)) as totalAmount
        FROM movimentacao m
        INNER JOIN conta c ON m.contaId = c.id
        WHERE m.date LIKE :mesAno || '%'
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