package com.gahee.hotchoco.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gahee.hotchoco.model.MarshMallow
import kotlinx.coroutines.flow.Flow

@Dao
interface MarshMallowDao {

    @Query("SELECT * FROM marsh_mallow ORDER BY date DESC")
    fun getMarshMallows(): Flow<List<MarshMallow>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(marshMallow: MarshMallow)

    @Query("DELETE FROM marsh_mallow")
    suspend fun deleteAll()
}
