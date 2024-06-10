package com.mohaberabi.sibha.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.mohaberabi.sibha.core.database.entity.ReminderEntity
import com.mohaberabi.sibha.core.domain.model.ReminderModel
import kotlinx.coroutines.flow.Flow


@Dao
interface ReminderDao {

    @Upsert
    suspend fun insertReminder(reminder: ReminderEntity): Long

    @Query("SELECT * FROM reminders")

    fun getAllReminder(): Flow<List<ReminderEntity>>

    @Query("DELETE FROM reminders WHERE id =:id")
    suspend fun deleteReminder(id: Long)


    @Query("UPDATE reminders SET enabled=:enabled WHERE id=:id")
    suspend fun updateReminder(id: Long, enabled: Boolean)
}