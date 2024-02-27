package br.com.renovatiu.cinedrivein.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.renovatiu.cinedrivein.data.local.dao.SessionDao
import br.com.renovatiu.cinedrivein.data.local.model.SessionEntity

@Database(
    entities = [SessionEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun sessionDao() : SessionDao
}