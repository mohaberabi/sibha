package com.mohaberabi.sibha.core.domain.repository

import com.mohaberabi.sibha.core.domain.model.ReminderModel
import com.mohaberabi.sibha.core.util.AppResult
import com.mohaberabi.sibha.core.util.DataError
import com.mohaberabi.sibha.core.util.EmptyDataResult
import kotlinx.coroutines.flow.Flow

interface ReminderRepository {


    suspend fun createReminder(reminder: ReminderModel): AppResult<Long, DataError>


    fun getAllReminders(): Flow<List<ReminderModel>>


    suspend fun deleteReminder(id: Long): EmptyDataResult<DataError>

    suspend fun updateReminder(id: Long, enabled: Boolean): EmptyDataResult<DataError>
}