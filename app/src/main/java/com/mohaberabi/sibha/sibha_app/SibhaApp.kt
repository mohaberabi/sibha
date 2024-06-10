package com.mohaberabi.sibha.sibha_app

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.content.getSystemService
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.mohaberabi.sibha.core.database.di.databaseModule
import com.mohaberabi.sibha.core.di.appModule
import com.mohaberabi.sibha.core.domain.notifications.SibhaNotiConst
import com.mohaberabi.sibha.features.backgrounds.di.backgroundsModule
import com.mohaberabi.sibha.features.font.di.fontModule
import com.mohaberabi.sibha.features.home.presentation.di.homeViewModelModule
import com.mohaberabi.sibha.features.notify_count.di.notifyCountModule
import com.mohaberabi.sibha.features.reminder.di.reminderModule
import com.mohaberabi.sibha.features.tasbeeh.di.tasbeehModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SibhaApp : Application() {

    val applicationScope = CoroutineScope(SupervisorJob())


    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@SibhaApp)
            modules(
                databaseModule,
                appModule,
                homeViewModelModule,
                tasbeehModule,
                backgroundsModule,
                fontModule,
                notifyCountModule,
                reminderModule,
            )
        }
        if (Build.VERSION.SDK_INT >= 26) {
            val notificationManager = getSystemService<NotificationManager>()
            val channel = NotificationChannel(
                SibhaNotiConst.SIBHA_DEFAULT_CHANNEL,
                SibhaNotiConst.SIBHA_DEFAULT_CHANNEL,
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager?.createNotificationChannel(channel)

        }
    }
}