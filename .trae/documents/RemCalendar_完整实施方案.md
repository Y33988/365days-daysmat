# 365days 完整实施方案

## 背景

365days 是一款 Android 日历应用，旨在完全复刻 Days Matter（倒数日）的核心功能，并采用现代化的 Glassmorphism（液态玻璃）UI 设计风格。

### 目标
- 实现 Days Matter 的全部核心功能
- 采用 Glassmorphism 设计风格，确保视觉一致性
- 支持用户自定义背景（相册选择/内置模板）
- 支持背景透明度和模糊程度调节
- 最低支持 Android 10.0，目标 Android 16
- 采用 BSD-3 开源许可协议

---

## 技术栈

- **语言**: Kotlin
- **UI 框架**: Jetpack Compose + Material 3
- **架构**: MVVM + Clean Architecture
- **本地存储**: Room Database + DataStore Preferences
- **依赖注入**: Hilt
- **导航**: Navigation Compose
- **图片加载**: Coil 3.1.0
- **日期处理**: kotlinx-datetime
- **通知**: Android AlarmManager + NotificationManager

---

## 核心功能模块

### 1. 事件管理模块
- **功能**: 添加、编辑、删除、查看事件
- **数据模型**: Event (id, title, date, time, location, description, category, color, isAllDay, reminderMinutes, repeatRule)
- **UI**: 
  - 事件列表视图（按日期排序）
  - 事件详情卡片
  - 事件编辑表单
- **关键文件**:
  - `data/model/Event.kt`
  - `data/dao/EventDao.kt`
  - `data/repository/EventRepository.kt`
  - `ui/event/EventListScreen.kt`
  - `ui/event/EventEditScreen.kt`
  - `ui/event/EventViewModel.kt`

### 2. 纪念日/倒数日模块
- **功能**: 记录重要日期，显示倒数/正数天数
- **数据模型**: Anniversary (id, title, date, category, icon, color, isTop, repeatInterval)
- **UI**:
  - 纪念日列表（纵向排列，支持置顶）
  - 纪念日编辑界面
  - 分类管理
- **关键文件**:
  - `data/model/Anniversary.kt`
  - `data/dao/AnniversaryDao.kt`
  - `data/repository/AnniversaryRepository.kt`
  - `ui/anniversary/AnniversaryListScreen.kt`
  - `ui/anniversary/AnniversaryEditScreen.kt`
  - `ui/anniversary/AnniversaryViewModel.kt`

### 3. 日历视图模块
- **功能**: 月视图、周视图、日视图切换
- **UI**:
  - MonthView: 网格布局显示月份
  - WeekView: 时间轴布局显示一周
  - DayView: 详细时间线布局
- **关键文件**:
  - `ui/calendar/MonthView.kt`
  - `ui/calendar/WeekView.kt`
  - `ui/calendar/DayView.kt`
  - `ui/calendar/CalendarViewModel.kt`

### 4. 提醒与通知模块
- **功能**: 事件提醒、定时通知
- **实现**:
  - AlarmManager 设置精确闹钟
  - BroadcastReceiver 接收闹钟触发
  - NotificationManager 发送通知
- **关键文件**:
  - `service/ReminderService.kt`
  - `receiver/AlarmReceiver.kt`
  - `util/NotificationHelper.kt`

### 5. 重复事件模块
- **功能**: 支持年、月、周、日重复规则
- **数据模型**: RepeatRule (type: DAILY/WEEKLY/MONTHLY/YEARLY, interval, endDate)
- **关键文件**:
  - `data/model/RepeatRule.kt`
  - `util/DateCalculator.kt`

### 6. 背景与主题模块
- **功能**: 
  - 自定义背景（相册选择/内置模板）
  - 背景透明度调节
  - 背景模糊程度调节
  - 强调色切换
- **UI**:
  - BackgroundPickerScreen: 背景选择界面
  - BackgroundLayer: 全局背景渲染层
- **关键文件**:
  - `data/model/BackgroundConfig.kt`
  - `ui/theme/BackgroundLayer.kt`
  - `ui/theme/BackgroundPickerScreen.kt`
  - `ui/theme/Theme.kt`
  - `ui/theme/Color.kt`

### 7. 设置模块
- **功能**:
  - 一周开始日（周日/周一）
  - 默认提醒时间
  - 强调色选择
  - 关于（BSD-3 许可证）
- **关键文件**:
  - `data/repository/SettingsRepository.kt`
  - `ui/settings/SettingsScreen.kt`

### 8. 搜索模块
- **功能**: 全局搜索事件和纪念日
- **关键文件**:
  - `ui/search/SearchScreen.kt`
  - `ui/search/SearchViewModel.kt`

---

## Glassmorphism UI 设计规范

### 核心视觉元素
1. **半透明模糊效果**: 
   - 背景透明度: 0.6-0.8
   - 模糊半径: 20-40dp
   - 使用 `androidx.compose.ui.graphics.RenderEffect`

2. **微妙边框**:
   - 边框宽度: 1dp
   - 边框颜色: 白色 20% 透明度
   - 圆角: 16dp

3. **磨砂质感**:
   - 使用 `BlendMode.Overlay` 混合模式
   - 添加细微噪点纹理

4. **阴影层次**:
   - 使用 `elevation` 属性
   - 多层阴影营造深度感

### 实现方式
- **BackgroundLayer.kt**: 全局背景层，应用模糊和透明度
- **GlassCard.kt**: 可复用的玻璃态卡片组件
- **GlassButton.kt**: 玻璃态按钮组件
- **GlassNavigationBar.kt**: 玻璃态导航栏

---

## 项目结构

```
app/src/main/java/com/remcalendar/
├── data/
│   ├── model/
│   │   ├── Event.kt
│   │   ├── Anniversary.kt
│   │   ├── RepeatRule.kt
│   │   └── BackgroundConfig.kt
│   ├── dao/
│   │   ├── EventDao.kt
│   │   └── AnniversaryDao.kt
│   ├── repository/
│   │   ├── EventRepository.kt
│   │   ├── AnniversaryRepository.kt
│   │   └── SettingsRepository.kt
│   └── database/
│       ├── AppDatabase.kt
│       └── Converters.kt
├── di/
│   └── AppModule.kt
├── service/
│   └── ReminderService.kt
├── receiver/
│   └── AlarmReceiver.kt
├── ui/
│   ├── theme/
│   │   ├── Theme.kt
│   │   ├── Color.kt
│   │   ├── BackgroundLayer.kt
│   │   └── BackgroundPickerScreen.kt
│   ├── components/
│   │   ├── GlassCard.kt
│   │   ├── GlassButton.kt
│   │   └── GlassNavigationBar.kt
│   ├── calendar/
│   │   ├── MonthView.kt
│   │   ├── WeekView.kt
│   │   ├── DayView.kt
│   │   └── CalendarViewModel.kt
│   ├── event/
│   │   ├── EventListScreen.kt
│   │   ├── EventEditScreen.kt
│   │   └── EventViewModel.kt
│   ├── anniversary/
│   │   ├── AnniversaryListScreen.kt
│   │   ├── AnniversaryEditScreen.kt
│   │   └── AnniversaryViewModel.kt
│   ├── settings/
│   │   └── SettingsScreen.kt
│   ├── search/
│   │   ├── SearchScreen.kt
│   │   └── SearchViewModel.kt
│   └── navigation/
│       └── NavGraph.kt
├── util/
│   ├── DateCalculator.kt
│   ├── NotificationHelper.kt
│   └── LunarCalendar.kt
└── MainActivity.kt
```

---

## 实施步骤

### 阶段 1: 项目初始化（第 1 天）
1. 创建 Android 项目结构
2. 配置 build.gradle.kts（依赖、插件）
3. 配置 AndroidManifest.xml（权限、组件）
4. 设置 Hilt 依赖注入

### 阶段 2: 数据层（第 2-3 天）
1. 定义数据模型（Event, Anniversary, RepeatRule, BackgroundConfig）
2. 创建 Room 数据库和 DAO
3. 实现 Repository 层
4. 实现 SettingsRepository（DataStore）

### 阶段 3: UI 基础组件（第 4-5 天）
1. 实现 Glassmorphism 核心组件（GlassCard, GlassButton）
2. 实现 BackgroundLayer（背景模糊、透明度）
3. 实现主题系统（Theme.kt, Color.kt）
4. 实现导航图（NavGraph.kt）

### 阶段 4: 核心功能（第 6-10 天）
1. 实现日历视图（MonthView, WeekView, DayView）
2. 实现事件管理（列表、编辑、删除）
3. 实现纪念日管理（列表、编辑、分类）
4. 实现搜索功能

### 阶段 5: 提醒与通知（第 11 天）
1. 实现 AlarmReceiver
2. 实现 ReminderService
3. 实现 NotificationHelper
4. 集成到事件和纪念日模块

### 阶段 6: 背景与主题（第 12 天）
1. 实现 BackgroundPickerScreen
2. 实现背景透明度/模糊调节
3. 实现强调色切换
4. 集成到全局 UI

### 阶段 7: 设置与优化（第 13 天）
1. 实现 SettingsScreen
2. 实现一周开始日设置
3. 实现默认提醒时间设置
4. 实现关于页面（BSD-3 许可证）

### 阶段 8: 测试与构建（第 14 天）
1. 功能测试
2. UI 适配测试（不同屏幕尺寸）
3. 性能优化
4. 生成 APK

---

## 验证方案

### 功能验证
1. **事件管理**: 添加、编辑、删除事件，验证数据持久化
2. **纪念日**: 创建倒数日/正数日，验证天数计算准确性
3. **日历视图**: 切换月/周/日视图，验证日期显示正确
4. **提醒通知**: 设置提醒，验证通知触发
5. **背景自定义**: 选择背景图片，调节透明度和模糊，验证效果
6. **设置**: 修改一周开始日、默认提醒时间、强调色，验证生效

### UI 验证
1. **Glassmorphism 效果**: 检查所有卡片、按钮、导航栏的模糊和透明度
2. **视觉一致性**: 确保所有组件风格统一
3. **屏幕适配**: 在不同尺寸设备上测试
4. **深色模式**: 验证深色模式下的显示效果

### 构建验证
1. 运行 `gradlew clean assembleDebug`
2. 验证 APK 生成成功
3. 在模拟器/真机上安装测试
4. 检查应用大小（目标 < 80MB）

---

## 关键依赖

```kotlin
// Compose
implementation(platform("androidx.compose:compose-bom:2026.06.00"))
implementation("androidx.compose.ui:ui")
implementation("androidx.compose.material3:material3")
implementation("androidx.compose.ui:ui-tooling-preview")

// Navigation
implementation("androidx.navigation:navigation-compose:2.8.0")

// Room
implementation("androidx.room:room-runtime:2.7.0")
kapt("androidx.room:room-compiler:2.7.0")

// DataStore
implementation("androidx.datastore:datastore-preferences:1.2.0")

// Hilt
implementation("com.google.dagger:hilt-android:2.51.1")
kapt("com.google.dagger:hilt-compiler:2.51.1")
implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

// Coil
implementation("io.coil-kt:coil-compose:3.1.0")

// kotlinx-datetime
implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.1")

// Lifecycle
implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.0")
implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.0")
```

---

## 许可证

BSD 3-Clause License

```
Copyright (c) 2026, 365days

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice,
   this list of conditions and the following disclaimer.

2. Redistributions in binary form must reproduce the above copyright notice,
   this list of conditions and the following disclaimer in the documentation
   and/or other materials provided with the distribution.

3. Neither the name of the copyright holder nor the names of its contributors
   may be used to endorse or promote products derived from this software
   without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
POSSIBILITY OF SUCH DAMAGE.
```

---

## 下一步行动

1. 创建项目目录结构
2. 配置 build.gradle.kts 和 settings.gradle.kts
3. 实现数据层（Room 数据库、DAO、Repository）
4. 实现 UI 基础组件（Glassmorphism 组件）
5. 逐步实现各功能模块
6. 测试和构建 APK

---

## 注意事项

1. **OneDrive 同步**: 确保项目文件正确同步到 OneDrive
2. **Gradle 路径**: 使用指定的 Gradle 路径 `C:\Usersdata\gradle9.5.1\gradle-9.5.1`
3. **SDK 路径**: 使用指定的 Android SDK 路径 `C:\Users\32450\AppData\Local\Android\Sdk`
4. **性能优化**: Glassmorphism 效果可能影响性能，需要在低端设备上测试
5. **权限**: 需要申请存储权限、通知权限、闹钟权限
