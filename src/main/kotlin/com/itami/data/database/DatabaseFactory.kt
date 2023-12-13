package com.itami.data.database

import com.itami.data.database.table.*
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.config.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {

    fun init(appConfig: ApplicationConfig) {
        val database = Database.connect(datasource = hikari(appConfig))

        transaction(db = database) {
            SchemaUtils.create(
                Users,
                Meals,
                Foods,
                ConsumedFoods,
                ConsumedWaters,
                Recipes
            )
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T {
        return newSuspendedTransaction(Dispatchers.IO) { block() }
    }

    private fun hikari(appConfig: ApplicationConfig): HikariDataSource {
        val config = HikariConfig()
        config.driverClassName = appConfig.property("storage.driverClassName").getString()
        config.jdbcUrl = appConfig.property("storage.jdbcURL").getString()
        config.password = appConfig.property("storage.password").getString()
        config.username = appConfig.property("storage.user").getString()
        config.maximumPoolSize = 3
        config.isAutoCommit = false
        config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        config.validate()

        return HikariDataSource(config)
    }

}