package com.mohaberabi.sibha.core.data.source

import android.database.sqlite.SQLiteFullException
import com.mohaberabi.sibha.core.database.dao.ReminderDao
import com.mohaberabi.sibha.core.database.entity.toReminder
import com.mohaberabi.sibha.core.database.entity.toReminderEntity
import com.mohaberabi.sibha.core.domain.datasource.ReminderLocalDataSource
import com.mohaberabi.sibha.core.domain.model.ReminderModel
import com.mohaberabi.sibha.core.util.AppResult
import com.mohaberabi.sibha.core.util.DataError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class RoomReminderLocalDataSource(
    private val reminderDao: ReminderDao,
) : ReminderLocalDataSource {
    override suspend fun createReminder(reminder: ReminderModel): AppResult<Long, DataError> {
        return try {
            val id = reminderDao.insertReminder(reminder.toReminderEntity())
            AppResult.Done(id)
        } catch (e: SQLiteFullException) {
            e.printStackTrace()
            AppResult.Error(DataError.Local.DISK_FULL)
        } catch (e: Exception) {
            e.printStackTrace()
            AppResult.Error(DataError.Local.UNKNOWN)
        }
    }

    override fun getAllReminders(): Flow<List<ReminderModel>> =
        reminderDao.getAllReminder()
            .map { list -> list.map { it.toReminder() } }

    override suspend fun deleteReminder(id: Long) = reminderDao.deleteReminder(id)


    override suspend fun updateReminder(id: Long, enabled: Boolean) {
        reminderDao.updateReminder(id, enabled)
    }

}