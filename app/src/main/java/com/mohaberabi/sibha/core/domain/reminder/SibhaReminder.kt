package com.mohaberabi.sibha.core.domain.reminder

import com.mohaberabi.sibha.core.data.reminder.IntervalSibhaReminderReciever
import com.mohaberabi.sibha.core.data.reminder.OneTimeSibhaReminderReciever


interface SibhaReminder {

    fun schaduleReminder(
        startAtMillis: Long,
        data: SibhaReminderData,
        recieverId: String
    )

    fun schaduleAndRepeatReminder(
        startAtMillis: Long,
        repeat: Int = 1,
        every: SibhaInterval = SibhaInterval.MINUTE,
        data: SibhaReminderData,
        recieverId: String
    )

    fun cancel(
        id: Int,
        receiverId: String
    )

    companion object {
        const val DEFAULT_REMINDER_TITLE_KEY = "reminderTitleKey"
        const val DEFAULT_REMINDER_BODY_KEY = "reminderBoyKey"
        const val ONE_TIME_REMINDER_RECEIEVER = "onTimeReminderReceiver"
        const val INTERVAL_REMINDER_RECEIEVER = "intervalReminderReciever"
        val remindersQualifers = mapOf(
            ONE_TIME_REMINDER_RECEIEVER to OneTimeSibhaReminderReciever::class.java,
            INTERVAL_REMINDER_RECEIEVER to IntervalSibhaReminderReciever::class.java
        )
    }
}


enum class SibhaInterval {
    MINUTE, HOUR, DAY,
}

data class SibhaReminderData(
    val title: String,
    val body: String,
    val id: Int
)