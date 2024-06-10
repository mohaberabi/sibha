package com.mohaberabi.sibha.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mohaberabi.sibha.core.domain.model.TasbeehModel


@Entity(tableName = "tasbeeh")
data class TasbeehEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val tasbeeh: String,
    val groupId: Long? = null,
    val count: Int,
)


fun TasbeehEntity.toTasbeehModel(): TasbeehModel = TasbeehModel(id, tasbeeh, groupId, count)
fun TasbeehModel.toTasbeehEntity(): TasbeehEntity = TasbeehEntity(id, tasbeeh, groupId, count)