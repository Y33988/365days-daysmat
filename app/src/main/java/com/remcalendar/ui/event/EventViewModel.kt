package com.remcalendar.ui.event

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.remcalendar.data.model.Event
import com.remcalendar.data.repository.EventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(
    private val eventRepository: EventRepository
) : ViewModel() {

    private val _editingEvent = MutableStateFlow<Event?>(null)
    val editingEvent: StateFlow<Event?> = _editingEvent

    fun loadEvent(eventId: Long) {
        viewModelScope.launch {
            _editingEvent.value = eventRepository.getEventById(eventId)
        }
    }

    suspend fun saveEvent(
        title: String,
        date: Long,
        time: Long?,
        location: String?,
        description: String?,
        category: String,
        color: Long,
        isAllDay: Boolean,
        reminderMinutes: Int?
    ) {
        val event = Event(
            id = _editingEvent.value?.id ?: 0,
            title = title,
            date = date,
            time = time,
            location = location,
            description = description,
            category = category,
            color = color,
            isAllDay = isAllDay,
            reminderMinutes = reminderMinutes
        )

        if (_editingEvent.value == null) {
            eventRepository.insertEvent(event)
        } else {
            eventRepository.updateEvent(event)
        }
    }

    fun deleteEvent(event: Event) {
        viewModelScope.launch {
            eventRepository.deleteEvent(event)
        }
    }

    fun clearEditingEvent() {
        _editingEvent.value = null
    }
}
