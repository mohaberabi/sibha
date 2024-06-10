package com.mohaberabi.sibha.util.fakes.source

import com.mohaberabi.sibha.core.domain.datasource.ReminderLocalDataSource
import com.mohaberabi.sibha.core.domain.model.ReminderModel
import com.mohaberabi.sibha.core.util.AppResult
import com.mohaberabi.sibha.core.util.DataError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeRemidnerLocalDataSource : ReminderLocalDataSource {


    var reminder = ReminderModel(
        id = 1L, title = "title", body = "body", hour = 1, minute = 1,
    )

    val reminderMap = mutableMapOf<Long, ReminderModel>(
        1L to reminder,
        2L to reminder.copy(2L)
    )

    var exception: Exception = Exception()

    var throwException: Boolean = false

    private var flow = MutableStateFlow(reminderMap.values.toList())

    override suspend fun createReminder(reminder: ReminderModel): AppResult<Long, DataError> {
        return if (throwException) {
            AppResult.Error(DataError.Local.UNKNOWN)
        } else {
            val id = 3L
            reminderMap[id] = reminder.copy(id = id)
            AppResult.Done(id)
        }
    }

    override fun getAllReminders(): Flow<List<ReminderModel>> = flow
    override suspend fun deleteReminder(id: Long) = doOrThrow {
        reminderMap.remove(id)
    }

    override suspend fun updateReminder(id: Long, enabled: Boolean) = doOrThrow {
        reminderMap[id] = reminderMap[id]!!.copy(enabled = enabled)
    }

    private fun doOrThrow(
        action: () -> Unit,
    ) {
        if (throwException) {
            throw exception
        } else {
            action()
        }
    }
}