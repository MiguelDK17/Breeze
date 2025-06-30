package com.migueldk17.breeze.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.migueldk17.breeze.entity.Conta
import kotlinx.coroutines.flow.Flow

@Dao
interface ContaDao {
    @Query("SELECT * FROM conta_table")
     fun getContas(): Flow<List<Conta>>
     //Pega as contas de um mes
     @Query("SELECT * FROM conta_table WHERE date_time LIKE :mesAno || '%'")
     fun getContasMes(mesAno: String): Flow<List<Conta>>
     //Terminar comando SQL e partir pro ViewModel
     @Query("SELECT * FROM conta_table WHERE id = :id LIMIT 1")
     suspend fun getContaById(id: Long): Conta
     //Insere a conta no Room
     @Insert(onConflict = OnConflictStrategy.REPLACE) //Caso haja conflito de IDS a mais recente subistitui a mais antiga
    suspend fun insertConta(conta: Conta): Long
    @Query("SELECT * from conta_table")
    fun getContasHistorico(): Flow<List<Conta>>
    //Atualiza o valor da conta
    @Update
    suspend fun atualizarConta(conta: Conta)

    //Apaga a conta pra sempre do Room
    @Delete
    suspend fun apagarConta(conta: Conta)
}