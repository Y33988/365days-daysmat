package com.remcalendar.data.model

data class RepeatRule(
    val type: RepeatType = RepeatType.NONE,
    val interval: Int = 1, // 间隔（如每 2 周）
    val endDate: Long? = null // 结束日期时间戳，null 表示永不结束
)

enum class RepeatType {
    NONE, // 不重复
    DAILY, // 每天
    WEEKLY, // 每周
    MONTHLY, // 每月
    YEARLY // 每年
}
