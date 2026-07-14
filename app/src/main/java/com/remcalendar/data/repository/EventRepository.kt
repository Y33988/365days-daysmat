package com.remcalendar.data.repository

import com.remcalendar.data.dao.EventDao
import com.remcalendar.data.model.Event
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EventRepository @Inject constructor(
    private val eventDao: EventDao
) {
    fun getAllEvents(): Flow<List<Event>> = eventDao.getAllEvents()

    suspend fun getEventById(id: Long): Event? = eventDao.getEventById(id)

    fun getEventsByDateRange(startDate: Long, endDate: Long): Flow<List<Event>> =
        eventDao.getEventsByDateRange(startDate, endDate)

    fun searchEvents(query: String): Flow<List<Event>> = eventDao.searchEvents(query)

    suspend fun insertEvent(event: Event): Long = eventDao.insertEvent(event)

    suspend fun updateEvent(event: Event) = eventDao.updateEvent(event)

    suspend fun deleteEvent(event: Event) = eventDao.deleteEvent(event)

    suspend fun deleteEventById(id: Long) = eventDao.deleteEventById(id)
}
