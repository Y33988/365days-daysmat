package com.remcalendar.ui.anniversary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.remcalendar.data.model.Anniversary
import com.remcalendar.data.model.RepeatInterval
import com.remcalendar.data.repository.AnniversaryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnniversaryViewModel @Inject constructor(
    private val anniversaryRepository: AnniversaryRepository
) : ViewModel() {

    private val _editingAnniversary = MutableStateFlow<Anniversary?>(null)
    val editingAnniversary: StateFlow<Anniversary?> = _editingAnniversary

    val anniversaries: StateFlow<List<Anniversary>> = anniversaryRepository.getAllAnniversaries()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun loadAnniversary(anniversaryId: Long) {
        viewModelScope.launch {
            _editingAnniversary.value = anniversaryRepository.getAnniversaryById(anniversaryId)
        }
    }

    suspend fun saveAnniversary(
        title: String,
        date: Long,
        category: String,
        icon: String,
        color: Long,
        isTop: Boolean,
        repeatInterval: RepeatInterval,
        isLunar: Boolean
    ) {
        val anniversary = Anniversary(
            id = _editingAnniversary.value?.id ?: 0,
            title = title,
            date = date,
            category = category,
            icon = icon,
            color = color,
            isTop = isTop,
            repeatInterval = repeatInterval,
            isLunar = isLunar
        )

        if (_editingAnniversary.value == null) {
            anniversaryRepository.insertAnniversary(anniversary)
        } else {
            anniversaryRepository.updateAnniversary(anniversary)
        }
    }

    fun deleteAnniversary(anniversary: Anniversary) {
        viewModelScope.launch {
            anniversaryRepository.deleteAnniversary(anniversary)
        }
    }

    fun toggleTopStatus(anniversary: Anniversary) {
        viewModelScope.launch {
            anniversaryRepository.updateTopStatus(anniversary.id, !anniversary.isTop)
        }
    }

    fun clearEditingAnniversary() {
        _editingAnniversary.value = null
    }
}
