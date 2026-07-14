package com.remcalendar.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.remcalendar.data.database.Converters

@Entity(tableName = "events")
@TypeConverters(Converters::class)
data class Event(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val date: Long, // 时间戳
    val time: Long? = null, // 时间戳，可为空（全天事件）
    val location: String? = null,
    val description: String? = null,
    val category: String = "默认",
    val color: Long = 0xFF2196F3, // 默认蓝色
    val isAllDay: Boolean = false,
    val reminderMinutes: Int? = null, // 提前提醒分钟数，null 表示不提醒
    val repeatRule: RepeatRule? = null,
    val createdAt: Long = System.currentTimeMillis()
)
