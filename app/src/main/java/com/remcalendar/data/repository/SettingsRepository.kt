package com.remcalendar.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.remcalendar.data.model.BackgroundConfig
import com.remcalendar.data.model.BackgroundType
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingsRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private object PreferencesKeys {
        val START_OF_WEEK = intPreferencesKey("start_of_week")
        val DEFAULT_REMINDER_MINUTES = intPreferencesKey("default_reminder_minutes")
        val ACCENT_COLOR = longPreferencesKey("accent_color")
        val BACKGROUND_TYPE = stringPreferencesKey("background_type")
        val BACKGROUND_IMAGE_PATH = stringPreferencesKey("background_image_path")
        val BACKGROUND_TEMPLATE = stringPreferencesKey("background_template")
        val BACKGROUND_TRANSPARENCY = floatPreferencesKey("background_transparency")
        val BACKGROUND_BLUR_RADIUS = floatPreferencesKey("background_blur_radius")
    }

    val startOfWeek: Flow<Int> = context.dataStore.data.map { preferences ->
        preferences[PreferencesKeys.START_OF_WEEK] ?: 0 // 0 = 周日, 1 = 周一
    }

    val defaultReminderMinutes: Flow<Int?> = context.dataStore.data.map { preferences ->
        preferences[PreferencesKeys.DEFAULT_REMINDER_MINUTES]
    }

    val accentColor: Flow<Long> = context.dataStore.data.map { preferences ->
        preferences[PreferencesKeys.ACCENT_COLOR] ?: 0xFF2196F3
    }

    val backgroundConfig: Flow<BackgroundConfig> = context.dataStore.data.map { preferences ->
        BackgroundConfig(
            type = BackgroundType.valueOf(
                preferences[PreferencesKeys.BACKGROUND_TYPE] ?: BackgroundType.BUILTIN.name
            ),
            imagePath = preferences[PreferencesKeys.BACKGROUND_IMAGE_PATH],
            builtinTemplate = preferences[PreferencesKeys.BACKGROUND_TEMPLATE] ?: "default",
            transparency = preferences[PreferencesKeys.BACKGROUND_TRANSPARENCY] ?: 1.0f,
            blurRadius = preferences[PreferencesKeys.BACKGROUND_BLUR_RADIUS] ?: 20f,
            accentColor = preferences[PreferencesKeys.ACCENT_COLOR] ?: 0xFF2196F3
        )
    }

    suspend fun setStartOfWeek(day: Int) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.START_OF_WEEK] = day
        }
    }

    suspend fun setDefaultReminderMinutes(minutes: Int?) {
        context.dataStore.edit { preferences ->
            if (minutes != null) {
                preferences[PreferencesKeys.DEFAULT_REMINDER_MINUTES] = minutes
            } else {
                preferences.remove(PreferencesKeys.DEFAULT_REMINDER_MINUTES)
            }
        }
    }

    suspend fun setAccentColor(color: Long) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.ACCENT_COLOR] = color
        }
    }

    suspend fun setBackgroundConfig(config: BackgroundConfig) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.BACKGROUND_TYPE] = config.type.name
            preferences[PreferencesKeys.BACKGROUND_IMAGE_PATH] = config.imagePath ?: ""
            preferences[PreferencesKeys.BACKGROUND_TEMPLATE] = config.builtinTemplate
            preferences[PreferencesKeys.BACKGROUND_TRANSPARENCY] = config.transparency
            preferences[PreferencesKeys.BACKGROUND_BLUR_RADIUS] = config.blurRadius
            preferences[PreferencesKeys.ACCENT_COLOR] = config.accentColor
        }
    }
}
