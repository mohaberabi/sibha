package com.mohaberabi.sibha.core.domain.datasource

import com.mohaberabi.sibha.core.util.const.SibhaConst
import com.mohaberabi.sibha.core.domain.model.TasbeehModel
import com.mohaberabi.sibha.core.util.DataError
import com.mohaberabi.sibha.core.util.EmptyDataResult
import kotlinx.coroutines.flow.Flow

interface TasbeehLocalDataSource {
    suspend fun addTasbeeh(tasbeeh: TasbeehModel): EmptyDataResult<DataError.Local>
    suspend fun deleteTasbeeh(id: Long)
    suspend fun updateTasbeehCount(tasbeehId: Long, count: Int): EmptyDataResult<DataError.Local>
    fun getAllTasbeeh(): Flow<List<TasbeehModel>>
    suspend fun getTasbeehsCount(): Int
    suspend fun insertDefaultTasbeehs(
        tasbeehs:
        List<TasbeehModel> = SibhaConst.defaultTasbeehs
    ): EmptyDataResult<DataError>

}