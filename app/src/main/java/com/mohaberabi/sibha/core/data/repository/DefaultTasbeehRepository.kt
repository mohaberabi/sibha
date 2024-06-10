package com.mohaberabi.sibha.core.data.repository

import com.mohaberabi.sibha.core.domain.datasource.TasbeehLocalDataSource
import com.mohaberabi.sibha.core.domain.model.TasbeehModel
import com.mohaberabi.sibha.core.domain.repository.TasbeehRepository
import com.mohaberabi.sibha.core.util.AppResult
import com.mohaberabi.sibha.core.util.DataError
import com.mohaberabi.sibha.core.util.EmptyDataResult
import kotlinx.coroutines.flow.Flow

class DefaultTasbeehRepository(
    private val tasbeehLocalDataSource: TasbeehLocalDataSource,
) : TasbeehRepository {

    override suspend fun addTasbeeh(tasbeeh: TasbeehModel): EmptyDataResult<DataError.Local> =
        tasbeehLocalDataSource.addTasbeeh(tasbeeh)

    override suspend fun deleteTasbeeh(id: Long) = tasbeehLocalDataSource.deleteTasbeeh(id)

    override suspend fun updateTasbeehCount(
        tasbeehId: Long,
        count: Int
    ): EmptyDataResult<DataError.Local> =
        tasbeehLocalDataSource.updateTasbeehCount(tasbeehId, count)

    override fun getAllTasbeeh(): Flow<List<TasbeehModel>> = tasbeehLocalDataSource.getAllTasbeeh()


    override suspend fun addDefaultTasbeehs(tasbeeehs: List<TasbeehModel>) =
        tasbeehLocalDataSource.insertDefaultTasbeehs(tasbeeehs)

    override suspend fun getTasbeehsCount(): Int = tasbeehLocalDataSource.getTasbeehsCount()
}