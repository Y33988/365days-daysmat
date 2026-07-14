package com.remcalendar.data.database

import androidx.room.TypeConverter
import com.remcalendar.data.model.RepeatInterval
import com.remcalendar.data.model.RepeatRule
import com.remcalendar.data.model.RepeatType

class Converters {
    @TypeConverter
    fun fromRepeatRule(repeatRule: RepeatRule?): String? {
        if (repeatRule == null) return null
        return "${repeatRule.type.name}|${repeatRule.interval}|${repeatRule.endDate ?: "null"}"
    }

    @TypeConverter
    fun toRepeatRule(value: String?): RepeatRule? {
        if (value == null) return null
        val parts = value.split("|")
        if (parts.size != 3) return null
        return RepeatRule(
            type = RepeatType.valueOf(parts[0]),
            interval = parts[1].toIntOrNull() ?: 1,
            endDate = if (parts[2] == "null") null else parts[2].toLongOrNull()
        )
    }

    @TypeConverter
    fun fromRepeatInterval(interval: RepeatInterval): String {
        return interval.name
    }

    @TypeConverter
    fun toRepeatInterval(value: String): RepeatInterval {
        return RepeatInterval.valueOf(value)
    }
}
