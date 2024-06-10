package com.mohaberabi.sibha.core.domain.model

import com.mohaberabi.sibha.core.domain.reminder.SibhaReminderData
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime


data class ReminderModel(
    val id: Long? = null,
    val title: String,
    val body: String,
    val hour: Int,
    val minute: Int,
    val enabled: Boolean = true,
) {
    fun timeAsMillis(): Long {

        val now = Clock.System.now()
        val localDateTime = now.toLocalDateTime(TimeZone.currentSystemDefault())

        val currentHour = localDateTime.hour
        val currentMinute = localDateTime.minute
        val timeInMillis = currentHour * 60 * 60 * 1000L + currentMinute * 60 * 1000L
        return timeInMillis

    }
}

val ReminderModel.toReminderData: SibhaReminderData
    get() = SibhaReminderData(title, body, id!!.toInt())