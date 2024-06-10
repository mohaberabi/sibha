package com.mohaberabi.sibha.core.domain.repository

import com.mohaberabi.sibha.core.util.const.SibhaConst
import com.mohaberabi.sibha.core.domain.model.TasbeehModel
import com.mohaberabi.sibha.core.util.DataError
import com.mohaberabi.sibha.core.util.EmptyDataResult
import kotlinx.coroutines.flow.Flow

interface TasbeehRepository {
    suspend fun addTasbeeh(tasbeeh: TasbeehModel): EmptyDataResult<DataError.Local>
    suspend fun deleteTasbeeh(id: Long)
    suspend fun updateTasbeehCount(tasbeehId: Long, count: Int): EmptyDataResult<DataError.Local>
    fun getAllTasbeeh(): Flow<List<TasbeehModel>>
    suspend fun addDefaultTasbeehs(tasbeeehs: List<TasbeehModel> = SibhaConst.defaultTasbeehs): EmptyDataResult<DataError>
    suspend fun getTasbeehsCount(): Int
}