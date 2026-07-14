# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.kts.

# Keep Room entities
-keep class com.remcalendar.data.model.** { *; }
-keep class com.remcalendar.data.database.** { *; }

# Keep Hilt generated classes
-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }
-keep class * extends dagger.hilt.android.internal.managers.ViewComponentManager$FragmentContextWrapper { *; }

# Keep Compose
-keep class androidx.compose.** { *; }

# Keep Kotlin coroutines
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}

# Keep Kotlin serialization
-keepclassmembers class * {
    public static ** Companion;
}
-keepclassmembers class * {
    public static final *** Companion;
}
-keepclasseswithmembers class * {
    public static ** Companion;
}
