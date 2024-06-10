package com.mohaberabi.sibha.util.generator

import com.mohaberabi.sibha.core.database.entity.ReminderEntity
import com.mohaberabi.sibha.core.database.entity.TasbeehEntity
import com.mohaberabi.sibha.core.domain.model.ReminderModel
import com.mohaberabi.sibha.core.domain.model.TasbeehModel


fun fakeReminderEntity(
    id: Long? = null,
    title: String = "titleTest",
    body: String = "bodyTest",
    min: Int = 1,
    hour: Int = 1,
): ReminderEntity = ReminderEntity(
    id = id,
    title = title,
    body = body,
    minute = min,
    hour = hour,
)

fun fakeReminderModel(
    id: Long? = null,
    title: String = "titleTest",
    body: String = "bodyTest",
    min: Int = 1,
    hour: Int = 1,
    enabled: Boolean = true,
): ReminderModel = ReminderModel(
    id = id,
    title = title,
    body = body,
    minute = min,
    enabled = enabled,
    hour = hour,
)


fun fakeTasbeehModel(
    id: Long? = null,
    count: Int = 0,
    tasbeeh: String = "tasbeehTest",
    groupId: Long? = null
): TasbeehModel = TasbeehModel(
    id = id,
    count = count,
    tasbeeh = tasbeeh,
    groupId = groupId,
)

fun fakeTasbeehEntity(
    id: Long? = null,
    count: Int = 0,
    tasbeeh: String = "tasbeehTest",
    groupId: Long? = null
): TasbeehEntity = TasbeehEntity(
    id = id,
    count = count,
    tasbeeh = tasbeeh,
    groupId = groupId,
)


