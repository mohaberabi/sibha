package com.mohaberabi.sibha.core.database.entity

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mohaberabi.sibha.core.domain.model.ReminderModel
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime


@Entity(
    tableName = "reminders"
)

data class ReminderEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val title: String,
    val body: String,
    val hour: Int,
    val minute: Int,
    val enabled: Boolean = true,
)


fun ReminderEntity.toReminder(): ReminderModel = ReminderModel(
    id = id,
    title = title,
    body = body,
    enabled = enabled,
    hour = hour,
    minute = minute
)

fun ReminderModel.toReminderEntity(): ReminderEntity = ReminderEntity(
    id = null,
    title = title,
    body = body,
    enabled = enabled,
    hour = hour,
    minute = minute
)