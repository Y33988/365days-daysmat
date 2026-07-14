package com.remcalendar.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.remcalendar.data.model.Anniversary
import com.remcalendar.data.model.Event
import com.remcalendar.data.repository.AnniversaryRepository
import com.remcalendar.data.repository.EventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val eventRepository: EventRepository,
    private val anniversaryRepository: AnniversaryRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    val events: StateFlow<List<Event>> = _searchQuery
        .flatMapLatest { query ->
            if (query.isEmpty()) {
                kotlinx.coroutines.flow.flowOf(emptyList())
            } else {
                eventRepository.searchEvents(query)
            }
        }
        .stateIn(viewModelScope, kotlinx.coroutines.flow.SharingStarted.Lazily, emptyList())

    val anniversaries: StateFlow<List<Anniversary>> = _searchQuery
        .flatMapLatest { query ->
            if (query.isEmpty()) {
                kotlinx.coroutines.flow.flowOf(emptyList())
            } else {
                anniversaryRepository.searchAnniversaries(query)
            }
        }
        .stateIn(viewModelScope, kotlinx.coroutines.flow.SharingStarted.Lazily, emptyList())

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }
}
