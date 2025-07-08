package com.migueldk17.breeze

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.migueldk17.breeze.dao.ContaDao
import com.migueldk17.breeze.dao.ParcelaDao
import com.migueldk17.breeze.dao.ReceitaDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): BreezeDatabase {

        val MIGRATION_8_9 = object : Migration(8, 9) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // Cria a nova tabela com a estrutura nova
                db.execSQL("""
                    CREATE TABLE receita_entity (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        valor REAL NOT NULL,
                        descricao TEXT NOT NULL,
                        data TEXT NOT NULL,
                        icon TEXT NOT NULL DEFAULT ''
                    )
                """.trimIndent())
                // Copia os dados da tabela saldo_table para a nova tabela
                db.execSQL("""
                    INSERTO INTO receita_entity (id, valor, descricao, data, icon)
                    SELECT id, valor, descricao, data, '' FROM saldo_table
                """.trimIndent())

                // Remove a tabela antiga
                db.execSQL("DROP TABLE saldo_table")
            }
        }

        return Room.databaseBuilder(
            context,
            BreezeDatabase::class.java,
            "breeze_database"
        ).addMigrations(MIGRATION_8_9)
            .build()

    }
    @Provides
    fun provideSaldoDao(database: BreezeDatabase): ReceitaDao {
        return database.receitaDao()
    }
    @Provides
    fun provideContaDao(database: BreezeDatabase): ContaDao {
        return database.contaDao()
    }
    @Provides
    fun provideParcelaDao(database: BreezeDatabase): ParcelaDao {
        return database.parcelaDao()
    }
}