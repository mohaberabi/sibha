package com.mohaberabi.sibha.core.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mohaberabi.sibha.core.database.dao.ReminderDao
import com.mohaberabi.sibha.core.database.dao.TasbeehDao
import com.mohaberabi.sibha.core.database.entity.ReminderEntity
import com.mohaberabi.sibha.core.database.entity.TasbeehEntity


@Database(
    entities = [
        TasbeehEntity::class,
        ReminderEntity::class,
    ],
    version = 1,
)
abstract class TasbeehDb : RoomDatabase() {
    abstract val tasbeehDao: TasbeehDao
    abstract val reminderDao: ReminderDao
}