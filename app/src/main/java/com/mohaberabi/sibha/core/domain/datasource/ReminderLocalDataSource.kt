package com.mohaberabi.sibha.core.domain.datasource

import com.mohaberabi.sibha.core.domain.model.ReminderModel
import com.mohaberabi.sibha.core.util.AppResult
import com.mohaberabi.sibha.core.util.DataError
import kotlinx.coroutines.flow.Flow


interface ReminderLocalDataSource {


    suspend fun createReminder(reminder: ReminderModel): AppResult<Long, DataError>


    fun getAllReminders(): Flow<List<ReminderModel>>


    suspend fun deleteReminder(id: Long)

    suspend fun updateReminder(id: Long, enabled: Boolean)
}