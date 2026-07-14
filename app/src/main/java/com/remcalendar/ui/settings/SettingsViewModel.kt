package com.remcalendar.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.remcalendar.data.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    val startOfWeek: StateFlow<Int> = settingsRepository.startOfWeek
        .stateIn(viewModelScope, SharingStarted.Lazily, 0)

    val defaultReminderMinutes: StateFlow<Int?> = settingsRepository.defaultReminderMinutes
        .stateIn(viewModelScope, SharingStarted.Lazily, null)

    fun setStartOfWeek(day: Int) {
        viewModelScope.launch {
            settingsRepository.setStartOfWeek(day)
        }
    }

    fun setDefaultReminderMinutes(minutes: Int?) {
        viewModelScope.launch {
            settingsRepository.setDefaultReminderMinutes(minutes)
        }
    }
}
