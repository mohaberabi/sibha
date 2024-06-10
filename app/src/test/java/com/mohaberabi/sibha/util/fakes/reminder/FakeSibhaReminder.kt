package com.mohaberabi.sibha.util.fakes.reminder

import com.mohaberabi.sibha.core.domain.reminder.SibhaInterval
import com.mohaberabi.sibha.core.domain.reminder.SibhaReminder
import com.mohaberabi.sibha.core.domain.reminder.SibhaReminderData


class FakeSibhaReminder : SibhaReminder {
    var called: Int = 0
        private set

    val reminders = mutableSetOf<Int>(0, 1)
    override fun schaduleReminder(
        startAtMillis: Long,
        data: SibhaReminderData,
        recieverId: String
    ) {
        called++
        reminders.add(data.id)
    }

    override fun schaduleAndRepeatReminder(
        startAtMillis: Long,
        repeat: Int,
        every: SibhaInterval,
        data: SibhaReminderData,
        recieverId: String
    ) {
        called++
        reminders.add(data.id)
    }

    override fun cancel(id: Int, receiverId: String) {
        called++
        reminders.remove(id)
    }

}