package com.remcalendar.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "anniversaries")
data class Anniversary(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val date: Long, // 时间戳
    val category: String = "纪念日",
    val icon: String = "🎉", // Emoji 图标
    val color: Long = 0xFF2196F3, // 默认蓝色
    val isTop: Boolean = false, // 是否置顶
    val repeatInterval: RepeatInterval = RepeatInterval.NONE, // 重复间隔
    val isLunar: Boolean = false, // 是否农历
    val createdAt: Long = System.currentTimeMillis()
)

enum class RepeatInterval {
    NONE, // 不重复
    YEARLY, // 每年
    MONTHLY, // 每月
    WEEKLY // 每周
}
