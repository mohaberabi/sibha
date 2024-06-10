package com.mohaberabi.sibha.util.fakes.repository

import com.mohaberabi.sibha.core.domain.model.ReminderModel
import com.mohaberabi.sibha.core.domain.repository.ReminderRepository
import com.mohaberabi.sibha.core.util.AppResult
import com.mohaberabi.sibha.core.util.DataError
import com.mohaberabi.sibha.core.util.EmptyDataResult
import com.mohaberabi.sibha.core.util.emptyDoneResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeReminderRepository : ReminderRepository {

    var called: Int = 0
        private set
    private val error = AppResult.Error(DataError.Local.UNKNOWN)


    var reminders = mutableMapOf<Long, Pair<Long, Boolean>>(
        0L to (0L to true),
        1L to (1L to true),
        2L to (2L to true)
    )
        private set
    var returnError: Boolean = false
    private val flow = MutableStateFlow(
        emptyList<ReminderModel>()
    )

    override suspend fun createReminder(
        reminder: ReminderModel,
    ): AppResult<Long, DataError> {
        called++
        return if (returnError) {
            error
        } else {
            reminders[reminder.id!!.toLong()] = (reminder.id!!.toLong() to true)
            AppResult.Done(3L)
        }
    }

    override fun getAllReminders(): Flow<List<ReminderModel>> {
        val reminds = reminders.keys.map { key ->
            ReminderModel(key, "test", "test", 1, 1, reminders[key]!!.second)
        }
        flow.value = reminds
        return flow
    }

    override suspend fun deleteReminder(
        id: Long,
    ): EmptyDataResult<DataError> = returnOrError {
        reminders.remove(id)
    }

    override suspend fun updateReminder(
        id: Long, enabled: Boolean,
    ): EmptyDataResult<DataError> =
        returnOrError {
            reminders[id] = reminders[id]!!.copy(second = enabled)
        }

    private fun returnOrError(
        action: () -> Unit,
    ): EmptyDataResult<DataError> {
        called++
        return if (returnError) {
            error
        } else {
            action()
            emptyDoneResult
        }
    }
}