package com.remcalendar.ui.theme

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.remcalendar.data.model.BackgroundConfig
import com.remcalendar.data.model.BackgroundType
import com.remcalendar.data.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.File
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class BackgroundPickerViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    val backgroundConfig: StateFlow<BackgroundConfig> = settingsRepository.backgroundConfig
        .stateIn(viewModelScope, SharingStarted.Lazily, BackgroundConfig())

    fun setBackgroundType(type: BackgroundType) {
        viewModelScope.launch {
            val current = backgroundConfig.value
            settingsRepository.setBackgroundConfig(current.copy(type = type))
        }
    }

    fun setBuiltinTemplate(template: String) {
        viewModelScope.launch {
            val current = backgroundConfig.value
            settingsRepository.setBackgroundConfig(
                current.copy(type = BackgroundType.BUILTIN, builtinTemplate = template)
            )
        }
    }

    fun setCustomImage(uri: Uri) {
        viewModelScope.launch {
            val savedPath = saveImageToInternalStorage(uri)
            val current = backgroundConfig.value
            settingsRepository.setBackgroundConfig(
                current.copy(type = BackgroundType.CUSTOM, imagePath = savedPath)
            )
        }
    }

    fun setTransparency(transparency: Float) {
        viewModelScope.launch {
            val current = backgroundConfig.value
            settingsRepository.setBackgroundConfig(current.copy(transparency = transparency.coerceIn(0f, 1f)))
        }
    }

    fun setBlurRadius(blurRadius: Float) {
        viewModelScope.launch {
            val current = backgroundConfig.value
            settingsRepository.setBackgroundConfig(current.copy(blurRadius = blurRadius.coerceIn(0f, 60f)))
        }
    }

    fun setAccentColor(color: Long) {
        viewModelScope.launch {
            val current = backgroundConfig.value
            settingsRepository.setBackgroundConfig(current.copy(accentColor = color))
        }
    }

    private fun saveImageToInternalStorage(uri: Uri): String {
        val inputStream = context.contentResolver.openInputStream(uri)
        val fileName = "bg_${UUID.randomUUID()}.jpg"
        val file = File(context.filesDir, fileName)
        inputStream?.use { input ->
            file.outputStream().use { output ->
                input.copyTo(output)
            }
        }
        return file.absolutePath
    }
}
