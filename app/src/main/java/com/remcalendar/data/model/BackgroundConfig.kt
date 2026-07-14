package com.remcalendar.data.model

data class BackgroundConfig(
    val type: BackgroundType = BackgroundType.BUILTIN,
    val imagePath: String? = null, // 自定义背景图片路径
    val builtinTemplate: String = "default", // 内置模板名称
    val transparency: Float = 1.0f, // 透明度 0.0-1.0
    val blurRadius: Float = 20f, // 模糊半径 0-40dp
    val accentColor: Long = 0xFF2196F3 // 强调色
)

enum class BackgroundType {
    BUILTIN, // 内置模板
    CUSTOM // 自定义图片
}
