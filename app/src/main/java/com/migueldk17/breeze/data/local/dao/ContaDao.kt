package com.migueldk17.breeze.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.migueldk17.breeze.data.local.entity.ContaEntity
import com.migueldk17.breeze.data.local.relation.ContaComParcelas
import kotlinx.coroutines.flow.Flow

@Dao
interface ContaDao {

    @Query("SELECT * FROM conta WHERE data_pagamento IS NULL")
    fun getContasComParcelas(): Flow<List<ContaComParcelas>>

     //Pega as contas de um mes
     @Query("SELECT * FROM conta WHERE date_time LIKE :mesAno || '%'")
     fun getContasMes(mesAno: String): Flow<List<ContaEntity>>

     //Terminar comando SQL e partir pro ViewModel
     @Query("SELECT * FROM conta WHERE id = :id LIMIT 1")
     suspend fun getContaById(id: Long): ContaEntity

     //Insere a conta no Room
     @Insert(onConflict = OnConflictStrategy.REPLACE) //Caso haja conflito de IDS a mais recente subistitui a mais antiga
    suspend fun insertConta(contaEntity: ContaEntity): Long

    @Query("SELECT * from conta")
    fun getContasHistorico(): Flow<List<ContaEntity>>

    //Atualiza o valor da conta
    @Update
    suspend fun atualizarConta(contaEntity: ContaEntity)

    //É feito o pagamento da conta
    @Query("UPDATE conta SET  data_pagamento = :data, forma_de_pagamento = :formaDePagamento WHERE id = :contaId")
    suspend fun efetuarPagamentoConta(data: String, contaId: Long, formaDePagamento: String): Int

    //Apaga a conta pra sempre do Room
    @Delete
    suspend fun apagarConta(contaEntity: ContaEntity)
}