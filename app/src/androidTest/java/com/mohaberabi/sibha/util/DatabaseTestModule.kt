package com.mohaberabi.sibha.util

import androidx.room.Room
import com.mohaberabi.sibha.core.data.source.RoomReminderLocalDataSource
import com.mohaberabi.sibha.core.data.source.RoomTasbeehDataSource
import com.mohaberabi.sibha.core.database.dao.ReminderDao
import com.mohaberabi.sibha.core.database.dao.TasbeehDao
import com.mohaberabi.sibha.core.database.db.TasbeehDb
import com.mohaberabi.sibha.core.domain.datasource.ReminderLocalDataSource
import com.mohaberabi.sibha.core.domain.datasource.TasbeehLocalDataSource
import org.koin.dsl.module


val databaseTestModule = module {
    single<TasbeehDb> {
        Room.inMemoryDatabaseBuilder(
            get(),
            TasbeehDb::class.java
        ).build()
    }
    single<TasbeehDao> {
        get<TasbeehDb>().tasbeehDao
    }
    single<ReminderDao> {
        get<TasbeehDb>().reminderDao
    }
    single {
        RoomTasbeehDataSource(get())
    }

    single {
        RoomReminderLocalDataSource(get())
    }
}