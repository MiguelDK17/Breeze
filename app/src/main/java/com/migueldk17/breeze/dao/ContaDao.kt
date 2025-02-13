package com.migueldk17.breeze.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.migueldk17.breeze.entity.Conta

@Dao
interface ContaDao {
    @Query("SELECT * FROM conta_table")
    suspend fun getConta(): Conta?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConta(conta: Conta)

    @Update
    suspend fun atualizarConta(conta: Conta)
}