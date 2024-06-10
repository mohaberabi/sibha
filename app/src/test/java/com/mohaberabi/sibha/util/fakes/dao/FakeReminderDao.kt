package com.mohaberabi.sibha.util.fakes.dao

import android.database.sqlite.SQLiteException
import com.mohaberabi.sibha.core.database.dao.ReminderDao
import com.mohaberabi.sibha.core.database.entity.ReminderEntity
import com.mohaberabi.sibha.util.generator.fakeReminderEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.jupiter.api.fail

class FakeReminderDao : ReminderDao {
    var id = 0L
    var reminderMap = mutableMapOf<Long, ReminderEntity>()
    var flow = MutableStateFlow(reminderMap.values.toList())

    val exception = Exception()
    var throwException: Boolean = false
    override suspend fun insertReminder(reminder: ReminderEntity): Long {

        return if (throwException) {
            throw exception
        } else {
            reminderMap[id] = reminder.copy(id = id)
            return id++
        }
    }

    override fun getAllReminder(): Flow<List<ReminderEntity>> {
        flow.value = listOf(fakeReminderEntity(id = 0L))
        return flow
    }

    override suspend fun deleteReminder(id: Long) {
        reminderMap.remove(id)
    }

    override suspend fun updateReminder(id: Long, enabled: Boolean) {
        reminderMap[id] = reminderMap[id]!!.copy(enabled = enabled)
    }
}