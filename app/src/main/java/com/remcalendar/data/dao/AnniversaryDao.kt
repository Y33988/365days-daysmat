package com.remcalendar.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.remcalendar.data.model.Anniversary
import kotlinx.coroutines.flow.Flow

@Dao
interface AnniversaryDao {
    @Query("SELECT * FROM anniversaries ORDER BY isTop DESC, date ASC")
    fun getAllAnniversaries(): Flow<List<Anniversary>>

    @Query("SELECT * FROM anniversaries WHERE id = :id")
    suspend fun getAnniversaryById(id: Long): Anniversary?

    @Query("SELECT * FROM anniversaries WHERE title LIKE '%' || :query || '%'")
    fun searchAnniversaries(query: String): Flow<List<Anniversary>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnniversary(anniversary: Anniversary): Long

    @Update
    suspend fun updateAnniversary(anniversary: Anniversary)

    @Delete
    suspend fun deleteAnniversary(anniversary: Anniversary)

    @Query("DELETE FROM anniversaries WHERE id = :id")
    suspend fun deleteAnniversaryById(id: Long)

    @Query("UPDATE anniversaries SET isTop = :isTop WHERE id = :id")
    suspend fun updateTopStatus(id: Long, isTop: Boolean)
}
