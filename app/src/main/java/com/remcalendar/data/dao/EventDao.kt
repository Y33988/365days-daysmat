package com.remcalendar.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.remcalendar.data.model.Event
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {
    @Query("SELECT * FROM events ORDER BY date ASC")
    fun getAllEvents(): Flow<List<Event>>

    @Query("SELECT * FROM events WHERE id = :id")
    suspend fun getEventById(id: Long): Event?

    @Query("SELECT * FROM events WHERE date BETWEEN :startDate AND :endDate ORDER BY date ASC")
    fun getEventsByDateRange(startDate: Long, endDate: Long): Flow<List<Event>>

    @Query("SELECT * FROM events WHERE title LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%'")
    fun searchEvents(query: String): Flow<List<Event>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event: Event): Long

    @Update
    suspend fun updateEvent(event: Event)

    @Delete
    suspend fun deleteEvent(event: Event)

    @Query("DELETE FROM events WHERE id = :id")
    suspend fun deleteEventById(id: Long)
}
