package com.migueldk17.breeze

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.migueldk17.breeze.data.local.dao.ContaDao
import com.migueldk17.breeze.data.local.dao.ParcelaDao
import com.migueldk17.breeze.data.local.dao.MovimentacaoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    val MIGRATION_20_21 = object : Migration(20, 21) {
        override fun migrate(db: SupportSQLiteDatabase) {

            db.execSQL("PRAGMA foreign_keys=OFF")

            // =========================
            // 🟢 CONTA
            // =========================
            db.execSQL("ALTER TABLE conta_table RENAME TO conta")

            // =========================
            // 🟣 PARCELA
            // =========================
            db.execSQL("""
            CREATE TABLE IF NOT EXISTS `parcela_new` (
                `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                `id_conta_pai` INTEGER NOT NULL,
                `valor` TEXT NOT NULL,
                `porcentagem_juros` TEXT NOT NULL,
                `numero_parcela` INTEGER NOT NULL,
                `total_parcelas` INTEGER NOT NULL,
                `data_vencimento` TEXT NOT NULL,
                `data_pagamento` TEXT,
                `forma_pagamento` TEXT,
                `esta_paga` INTEGER NOT NULL DEFAULT 0,
                FOREIGN KEY(`id_conta_pai`) REFERENCES `conta`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE
            )
        """.trimIndent())

            db.execSQL("""
            INSERT INTO parcela_new (
                id, id_conta_pai, valor, porcentagem_juros,
                numero_parcela, total_parcelas,
                data_vencimento, data_pagamento,
                forma_pagamento, esta_paga
            )
            SELECT
                id, id_conta_pai, valor, porcentagem_juros,
                numero_parcela, total_parcelas,
                data_vencimento, data_pagamento,
                forma_pagamento, esta_paga
            FROM parcela_entity
        """)

            db.execSQL("DROP TABLE parcela_entity")
            db.execSQL("ALTER TABLE parcela_new RENAME TO parcela")
            db.execSQL("CREATE INDEX IF NOT EXISTS `index_parcela_id_conta_pai` ON `parcela` (`id_conta_pai`)")

            // =========================
            // 🔵 MOVIMENTACAO
            // =========================
            db.execSQL("""
            CREATE TABLE IF NOT EXISTS `movimentacao_new` (
                `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                `valor` TEXT NOT NULL,
                `descricao` TEXT NOT NULL,
                `date` TEXT NOT NULL,
                `tipo` INTEGER NOT NULL, 
                `icon` TEXT NOT NULL,
                `contaId` INTEGER,
                FOREIGN KEY(`contaId`) REFERENCES `conta`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE
            )
        """.trimIndent())

            // Aqui está o "pulo do gato": no INSERT (destino) usamos 'date',
            // mas no SELECT (origem) usamos 'data' para mapear corretamente os valores.
            db.execSQL("""
            INSERT INTO movimentacao_new (
                id, valor, descricao, date, tipo, icon, contaId
            )
            SELECT
                id, valor, descricao, data, tipo, icon, contaId
            FROM movimentacao_entity
        """)

            db.execSQL("DROP TABLE movimentacao_entity")
            db.execSQL("ALTER TABLE movimentacao_new RENAME TO movimentacao")
            db.execSQL("CREATE INDEX IF NOT EXISTS `index_movimentacao_contaId` ON `movimentacao` (`contaId`)")

            db.execSQL("PRAGMA foreign_keys=ON")
        }
    }
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): BreezeDatabase {



        return Room.databaseBuilder(
            context,
            BreezeDatabase::class.java,
            "breeze_database"
        )
            .addMigrations(MIGRATION_20_21)
            .build()

    }
    @Provides
    fun provideSaldoDao(database: BreezeDatabase): MovimentacaoDao {
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