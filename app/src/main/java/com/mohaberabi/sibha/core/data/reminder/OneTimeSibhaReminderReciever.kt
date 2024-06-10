package com.mohaberabi.sibha.core.data.reminder

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.mohaberabi.sibha.core.data.notifications.SibhaAndroidNotificationsManager
import com.mohaberabi.sibha.core.domain.notifications.SibhaNotificationManager
import com.mohaberabi.sibha.core.domain.reminder.SibhaReminder

class OneTimeSibhaReminderReciever(
) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context != null) {
            val sibhaNotificationManager = SibhaAndroidNotificationsManager(context = context)
            val title = intent?.getStringExtra(SibhaReminder.DEFAULT_REMINDER_TITLE_KEY) ?: ""
            val body = intent?.getStringExtra(SibhaReminder.DEFAULT_REMINDER_BODY_KEY) ?: ""
            sibhaNotificationManager.show(title, body)
        }
    }
}