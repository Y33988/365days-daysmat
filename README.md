# 365days

一款功能完整、采用 **iOS 26/27 风格液态玻璃（Liquid Glass / Glassmorphism）UI** 的安卓日历应用。

> 复刻 Days Matter（倒数日）核心功能，并扩展为完整的日程管理 + 纪念日管理工具。

---

## ✨ 主要功能

### 📅 日历与日程
- 年 / 月 / 周 / 日 多种视图切换
- 事件增删改查，支持全天事件、具体时间、地点、描述
- 事件提醒通知（BootReceiver 自启动）
- 搜索事件与纪念日

### 🎉 纪念日 / 倒数日
- 添加纪念日，自动计算距今 / 剩余天数
- 支持置顶、农历、分类、图标、颜色
- 支持按年 / 月 / 周重复

### 🎨 深度自定义背景
- 内置 6 款精美渐变模板
- 从设备相册选择自定义背景
- 背景透明度调节
- 背景模糊程度调节（Android 12+ 原生模糊）
- 8 种强调色切换

### 🧊 液态玻璃 UI
- 半透明磨砂玻璃卡片
- 顶部高光、底部反光、边缘折射
- 统一的玻璃态按钮、浮动按钮、底部导航栏
- 顶部光晕与底部暗角背景层次
- 全界面透明背景，确保背景透射一致

---

## 📱 系统要求

- **最低支持**：Android 10.0（API 29）
- **目标版本**：Android 16（API 36）
- 推荐 Android 12+ 以获得最佳模糊效果

---

## 🛠 技术栈

| 层级 | 技术 |
|------|------|
| 语言 | Kotlin |
| UI | Jetpack Compose + Material 3 |
| 架构 | MVVM + Hilt 依赖注入 |
| 数据持久化 | Room + DataStore |
| 异步 | Kotlin Coroutines / Flow |
| 图片加载 | Coil |
| 构建 | Gradle 9.5.1 |

---

## 🚀 构建与运行

### 环境配置
- Gradle：`C:\Usersdata\gradle9.5.1\gradle-9.5.1`
- Android SDK：`C:\Users\32450\AppData\Local\Android\Sdk`

### 构建 Debug APK

```bash
# 在项目根目录执行
C:\Usersdata\gradle9.5.1\gradle-9.5.1\bin\gradle.bat assembleDebug
```

构建成功后，APK 位于：

```
app/build/outputs/apk/debug/app-debug.apk
```

### 安装到设备

```bash
adb install app/build/outputs/apk/debug/app-debug.apk
```

---

## 📂 项目结构

```
app/src/main/java/com/remcalendar/
├── MainActivity.kt              # 主界面 + 底部导航
├── data/                        # 数据层
│   ├── model/                   # 数据模型（Event、Anniversary、BackgroundConfig）
│   ├── repository/              # Repository（SettingsRepository、EventRepository 等）
│   └── database/                # Room 数据库
├── ui/                          # UI 层
│   ├── calendar/                # 日历相关（YearView、MonthView、WeekView、DayView）
│   ├── event/                   # 事件列表与编辑
│   ├── anniversary/             # 纪念日列表与编辑
│   ├── settings/                # 设置页
│   ├── search/                  # 搜索页
│   ├── theme/                   # 主题、背景层、背景选择器
│   └── components/              # 可复用玻璃态组件
└── util/                        # 工具类（屏幕尺寸自适应）
```

---

## 🎨 设计参考

- **iOS 26 / iOS 27 Liquid Glass** 设计语言
- 强调玻璃材质的光透射、折射、高光与阴影层次
- 深色主题为主，确保文字在复杂背景下清晰可读

---

## 📄 开源许可

本项目采用 **BSD-3-Clause** 开源许可协议。

```
BSD 3-Clause License

Copyright (c) 2026, 365days Contributors
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this
   list of conditions and the following disclaimer.

2. Redistributions in binary form must reproduce the above copyright notice,
   this list of conditions and the following disclaimer in the documentation
   and/or other materials provided with the distribution.

3. Neither the name of the copyright holder nor the names of its
   contributors may be used to endorse or promote products derived from
   this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
```

---

## 🤝 贡献

欢迎提交 Issue 和 Pull Request。

## 📧 联系

如有问题或建议，请在项目仓库中提交 Issue。
