package com.remcalendar.ui.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.remcalendar.data.model.BackgroundConfig
import com.remcalendar.data.model.Event
import com.remcalendar.data.repository.EventRepository
import com.remcalendar.data.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

enum class CalendarViewMode { YEAR, MONTH, WEEK, DAY }

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val eventRepository: EventRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _currentDate = MutableStateFlow(Calendar.getInstance())
    val currentDate: StateFlow<Calendar> = _currentDate

    private val _viewMode = MutableStateFlow(CalendarViewMode.MONTH)
    val viewMode: StateFlow<CalendarViewMode> = _viewMode

    val events = eventRepository.getAllEvents()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val backgroundConfig: StateFlow<BackgroundConfig> = settingsRepository.backgroundConfig
        .stateIn(viewModelScope, SharingStarted.Lazily, BackgroundConfig())

    val startOfWeek = settingsRepository.startOfWeek
        .stateIn(viewModelScope, SharingStarted.Lazily, 0)

    fun setViewMode(mode: CalendarViewMode) {
        _viewMode.value = mode
    }

    fun previousPeriod() {
        val calendar = _currentDate.value.clone() as Calendar
        when (_viewMode.value) {
            CalendarViewMode.YEAR -> calendar.add(Calendar.YEAR, -1)
            CalendarViewMode.MONTH -> calendar.add(Calendar.MONTH, -1)
            CalendarViewMode.WEEK -> calendar.add(Calendar.WEEK_OF_YEAR, -1)
            CalendarViewMode.DAY -> calendar.add(Calendar.DAY_OF_MONTH, -1)
        }
        _currentDate.value = calendar
    }

    fun nextPeriod() {
        val calendar = _currentDate.value.clone() as Calendar
        when (_viewMode.value) {
            CalendarViewMode.YEAR -> calendar.add(Calendar.YEAR, 1)
            CalendarViewMode.MONTH -> calendar.add(Calendar.MONTH, 1)
            CalendarViewMode.WEEK -> calendar.add(Calendar.WEEK_OF_YEAR, 1)
            CalendarViewMode.DAY -> calendar.add(Calendar.DAY_OF_MONTH, 1)
        }
        _currentDate.value = calendar
    }

    fun goToToday() {
        _currentDate.value = Calendar.getInstance()
    }

    fun setSelectedDate(date: Calendar) {
        _currentDate.value = date.clone() as Calendar
    }

    fun deleteEvent(event: Event) {
        viewModelScope.launch {
            eventRepository.deleteEvent(event)
        }
    }
}
