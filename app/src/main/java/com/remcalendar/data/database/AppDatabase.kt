package com.remcalendar.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.remcalendar.data.dao.AnniversaryDao
import com.remcalendar.data.dao.EventDao
import com.remcalendar.data.model.Anniversary
import com.remcalendar.data.model.Event

@Database(
    entities = [Event::class, Anniversary::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun eventDao(): EventDao
    abstract fun anniversaryDao(): AnniversaryDao
}
