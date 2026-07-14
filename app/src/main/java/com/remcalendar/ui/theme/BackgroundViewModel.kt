package com.remcalendar.ui.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.remcalendar.data.model.BackgroundConfig
import com.remcalendar.data.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class BackgroundViewModel @Inject constructor(
    settingsRepository: SettingsRepository
) : ViewModel() {

    val backgroundConfig: StateFlow<BackgroundConfig> = settingsRepository.backgroundConfig
        .stateIn(viewModelScope, SharingStarted.Lazily, BackgroundConfig())
}
