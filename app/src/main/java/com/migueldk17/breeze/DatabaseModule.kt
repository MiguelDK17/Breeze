package com.migueldk17.breeze

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.migueldk17.breeze.dao.ContaDao
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
        val MIGRATION_3_4 = object : Migration(3, 4){
            override fun migrate(db: SupportSQLiteDatabase) {
                // Adiciona coluna isContaParcelada na tabela Conta
                db.execSQL("ALTER TABLE conta_table ADD COLUMN is_conta_parcelada INTEGER NOT NULL DEFAULT 0")

                //Cria a nova tabela ParcelaEntity
                db.execSQL(
                    """
                        CREATE TABLE IF NOT EXISTS parcela_entity (
                        id INTEGER PRIMARY KEY NOT NULL,
                        id_conta_pai TEXT NOT NULL,
                        valor REAL NOT NULL,
                        numero_parcela INTEGER NOT NULL,
                        total_parcelas INTEGER NOT NULL,
                        data TEXT NOT NULL,
                        esta_paga INTEGER NOT NULL DEFAULT 0
                        )
                    """.trimIndent())
            }
        }

        return Room.databaseBuilder(
            context,
            BreezeDatabase::class.java,
            "breeze_database"
        ).addMigrations(MIGRATION_3_4)
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
}