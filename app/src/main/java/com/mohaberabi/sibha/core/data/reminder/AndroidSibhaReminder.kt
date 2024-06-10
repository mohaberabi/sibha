package com.mohaberabi.sibha.core.data.reminder

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.content.getSystemService
import com.mohaberabi.sibha.core.domain.reminder.SibhaInterval
import com.mohaberabi.sibha.core.domain.reminder.SibhaReminder
import com.mohaberabi.sibha.core.domain.reminder.SibhaReminderData


class AndroidSibhaReminder(
    private val context: Context,
) : SibhaReminder {
    private val alarmManager = context.getSystemService<AlarmManager>()!!
    override fun schaduleReminder(
        startAtMillis: Long,
        data: SibhaReminderData,
        recieverId: String
    ) = alarmManager.setExactAndAllowWhileIdle(
        AlarmManager.RTC_WAKEUP,
        startAtMillis,
        defaultReminderPendingIntent(
            recieverId,
            data.title,
            data.body,
            data.id
        )
    )

    override fun schaduleAndRepeatReminder(
        startAtMillis: Long,
        repeat: Int,
        every: SibhaInterval,
        data: SibhaReminderData,
        recieverId: String
    ) {
        val repeatEvery = when (every) {
            SibhaInterval.MINUTE -> 1000 * 60
            SibhaInterval.HOUR -> 1000 * 60 * 60
            SibhaInterval.DAY -> (1000 * 60 * 60) * 24
        } * repeat

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            startAtMillis,
            repeatEvery.toLong(),
            defaultReminderPendingIntent(
                recieverId,
                title = data.title,
                body = data.body,
                data.id
            )
        )
    }


    private fun defaultReminderPendingIntent(
        recieverId: String,
        title: String,
        body: String,
        requestCode: Int
    ): PendingIntent {
        val intent = Intent(
            context,
            SibhaReminder.remindersQualifers[recieverId]
        )
            .apply {
                putExtra(
                    SibhaReminder.DEFAULT_REMINDER_TITLE_KEY,
                    title,
                )
                putExtra(
                    SibhaReminder.DEFAULT_REMINDER_BODY_KEY,
                    body,
                )
            }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        return pendingIntent
    }


    override fun cancel(
        id: Int,
        receiverId: String,
    ) {
        val intent = Intent(context, SibhaReminder.remindersQualifers[receiverId])
        val pendingIntent =
            PendingIntent.getBroadcast(
                context,
                id,
                intent,
                PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
            )
        pendingIntent?.let {
            alarmManager.cancel(it)
            it.cancel()
        }
    }

}
