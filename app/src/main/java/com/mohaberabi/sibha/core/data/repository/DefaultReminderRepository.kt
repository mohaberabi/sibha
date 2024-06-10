package com.mohaberabi.sibha.core.data.repository

import com.mohaberabi.sibha.core.domain.datasource.ReminderLocalDataSource
import com.mohaberabi.sibha.core.domain.model.ReminderModel
import com.mohaberabi.sibha.core.domain.repository.ReminderRepository
import com.mohaberabi.sibha.core.util.AppResult
import com.mohaberabi.sibha.core.util.DataError
import com.mohaberabi.sibha.core.util.EmptyDataResult
import kotlinx.coroutines.flow.Flow

class DefaultReminderRepository(
    private val reminderLocalDataSource: ReminderLocalDataSource,
) : ReminderRepository {
    override suspend fun createReminder(reminder: ReminderModel): AppResult<Long, DataError> =
        reminderLocalDataSource.createReminder(reminder)

    override fun getAllReminders(): Flow<List<ReminderModel>> =
        reminderLocalDataSource.getAllReminders()

    override suspend fun deleteReminder(id: Long): EmptyDataResult<DataError> {
        return try {
            reminderLocalDataSource.deleteReminder(id)
            AppResult.Done(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            AppResult.Error(DataError.Local.UNKNOWN)
        }
    }


    override suspend fun updateReminder(id: Long, enabled: Boolean): EmptyDataResult<DataError> {
        return try {
            reminderLocalDataSource.updateReminder(id, enabled)
            AppResult.Done(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            AppResult.Error(DataError.Local.UNKNOWN)
        }
    }
}