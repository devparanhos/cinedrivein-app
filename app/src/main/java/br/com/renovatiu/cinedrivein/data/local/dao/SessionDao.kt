package br.com.renovatiu.cinedrivein.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.renovatiu.cinedrivein.data.local.model.SessionEntity

@Dao
interface SessionDao {

    @Insert
    suspend fun insert(sessionEntity: SessionEntity) : Long

    @Query("SELECT * FROM session")
    suspend fun getAll() : List<SessionEntity>

    @Query("DELETE FROM session")
    suspend fun deleteAll()

}