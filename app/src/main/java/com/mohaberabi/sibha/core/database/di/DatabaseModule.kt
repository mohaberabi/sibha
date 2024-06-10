package com.mohaberabi.sibha.core.database.di

import androidx.room.Room
import com.mohaberabi.sibha.core.data.source.RoomTasbeehDataSource
import com.mohaberabi.sibha.core.database.db.TasbeehDb
import com.mohaberabi.sibha.core.domain.datasource.TasbeehLocalDataSource
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module


val databaseModule = module {


    single {


        Room.databaseBuilder(
            androidApplication(),
            TasbeehDb::class.java, "sebha.db"
        ).build()
    }
    single {
        get<TasbeehDb>().tasbeehDao
    }
    single<TasbeehLocalDataSource> {
        RoomTasbeehDataSource(get())
    }
    single {
        get<TasbeehDb>().reminderDao
    }


}