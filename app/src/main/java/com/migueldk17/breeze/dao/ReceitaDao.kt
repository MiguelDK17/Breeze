package com.migueldk17.breeze.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.migueldk17.breeze.entity.Receita
import kotlinx.coroutines.flow.Flow

@Dao
interface ReceitaDao {
    //Insere um objeto saldo na tabela
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserirReceita(receita: Receita)

    //Pega a soma de todos os valores em saldo_table
    @Query("SELECT SUM(valor) FROM saldo_table")
    fun getSaldoTotal(): Flow<Double?>

    //Busca o primeiro registro da tabela saldo_table
    @Query("SELECT * FROM saldo_table ORDER BY data DESC")
    suspend fun getTodasAsReceitas(): List<Receita>? //Retorna null se a tabela estiver vazia
}