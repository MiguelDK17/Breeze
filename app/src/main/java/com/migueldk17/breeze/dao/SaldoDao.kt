package com.migueldk17.breeze.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.migueldk17.breeze.entity.Saldo

@Dao
interface SaldoDao {
    //Busca o primeiro registro da tabela saldo_table
    @Query("SELECT * FROM saldo_table LIMIT 1")
    suspend fun getSaldo(): Saldo? //Retorna null se a tabela estiver vazia

    //Insere um objeto saldo na tabela
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserirSaldo(saldo: Saldo)

    //Atualiza os valores existentes com base na chave primária
    @Update
    suspend fun atualizarSaldo(saldo: Saldo)
}