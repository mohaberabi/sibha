package com.mohaberabi.sibha.core.di

import com.mohaberabi.sibha.core.data.image_convertor.AndroidImageConvertor
import com.mohaberabi.sibha.core.data.image_storage.AndroidImageStorage
import com.mohaberabi.sibha.core.data.vibration.AndroidSibhaVibrator
import com.mohaberabi.sibha.core.data.repository.DefaultTasbeehRepository
import com.mohaberabi.sibha.core.data.mediaplayer.TasbeehAndroidMediaPlayer
import com.mohaberabi.sibha.core.data.notifications.SibhaAndroidNotificationsManager
import com.mohaberabi.sibha.core.data.reminder.AndroidSibhaReminder
import com.mohaberabi.sibha.core.data.repository.DefaultReminderRepository
import com.mohaberabi.sibha.core.data.repository.DefaultUserRepository
import com.mohaberabi.sibha.core.data.source.RoomReminderLocalDataSource
import com.mohaberabi.sibha.core.data.source.UserDataStore
import com.mohaberabi.sibha.core.domain.datasource.ReminderLocalDataSource
import com.mohaberabi.sibha.core.domain.datasource.UserLocalDataSource
import com.mohaberabi.sibha.core.domain.image_convertor.ImageConvertor
import com.mohaberabi.sibha.core.domain.image_storage.ImageStorage
import com.mohaberabi.sibha.core.domain.vibration.SibhaVibrator
import com.mohaberabi.sibha.core.domain.media_player.TasbeehMediaPlayer
import com.mohaberabi.sibha.core.domain.notifications.SibhaNotificationManager
import com.mohaberabi.sibha.core.domain.reminder.SibhaReminder
import com.mohaberabi.sibha.core.domain.repository.ReminderRepository
import com.mohaberabi.sibha.core.domain.repository.TasbeehRepository
import com.mohaberabi.sibha.core.domain.repository.UserRepository
import com.mohaberabi.sibha.core.util.DefaultSibhaCoroutineDispatchers
import com.mohaberabi.sibha.core.util.SibhaCoroutineDispatchers
import com.mohaberabi.sibha.sibha_app.SibhaApp
import com.mohaberabi.sibha.sibha_app.dataStore
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module


val appModule = module {


    single<ImageStorage> {
        AndroidImageStorage(get(), get())
    }
    single {
        (androidApplication() as SibhaApp).applicationScope
    }
    single<UserLocalDataSource> {
        UserDataStore(androidApplication().dataStore, get())
    }

    single<ReminderLocalDataSource> {
        RoomReminderLocalDataSource(get())
    }
    single<TasbeehMediaPlayer> { TasbeehAndroidMediaPlayer(get(), get()) }

    single<SibhaCoroutineDispatchers> { DefaultSibhaCoroutineDispatchers() }

    single<TasbeehRepository> {
        DefaultTasbeehRepository(get())
    }

    single<UserRepository> {
        DefaultUserRepository(get(), get(), get())
    }

    single<ReminderRepository> {
        DefaultReminderRepository(get())
    }
    single<SibhaVibrator> {
        AndroidSibhaVibrator(get())
    }
    single<ImageConvertor> {
        AndroidImageConvertor(get(), get())
    }
    single<SibhaNotificationManager> {
        SibhaAndroidNotificationsManager(get())
    }

    single<SibhaReminder> {
        AndroidSibhaReminder(get())
    }


}
