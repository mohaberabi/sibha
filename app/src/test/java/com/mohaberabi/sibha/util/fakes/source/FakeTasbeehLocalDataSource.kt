package com.mohaberabi.sibha.util.fakes.source

import com.mohaberabi.sibha.core.domain.datasource.TasbeehLocalDataSource
import com.mohaberabi.sibha.core.domain.model.TasbeehModel
import com.mohaberabi.sibha.core.util.AppResult
import com.mohaberabi.sibha.core.util.DataError
import com.mohaberabi.sibha.core.util.EmptyDataResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class FakeTasbeehLocalDataSource : TasbeehLocalDataSource {


    var tasbeeh = TasbeehModel(null, "tasbeeh", null, 0)

    var tasbeehsMap = mutableMapOf(
        1L to tasbeeh.copy(id = 1L, groupId = 1000),
        2L to tasbeeh.copy(id = 2L, groupId = 1000),
        3L to tasbeeh.copy(id = 3L),
        4L to tasbeeh.copy(id = 4L),
    )
    var flow = MutableStateFlow(tasbeehsMap.values.toList())
    var exception: Exception = Exception()
    var throwException: Boolean = false


    override suspend fun addTasbeeh(tasbeeh: TasbeehModel): EmptyDataResult<DataError.Local> {
        return if (throwException) {
            AppResult.Error(DataError.Local.UNKNOWN)
        } else {
            tasbeehsMap[6L] = tasbeeh
            AppResult.Done(Unit)
        }
    }

    override suspend fun deleteTasbeeh(id: Long) {
        tasbeehsMap.remove(id)
    }

    override suspend fun updateTasbeehCount(
        tasbeehId: Long,
        count: Int
    ): EmptyDataResult<DataError.Local> {
        return if (throwException) {
            AppResult.Error(DataError.Local.UNKNOWN)
        } else {
            tasbeehsMap[tasbeehId] = tasbeehsMap[tasbeehId]!!.copy(count = count)
            AppResult.Done(Unit)
        }
    }

    override fun getAllTasbeeh(): Flow<List<TasbeehModel>> = flow

    override fun getAllTasbeehByGroupId(id: Long): Flow<List<TasbeehModel>> {
        return flow.map { list ->
            list.filter { tasbeeh ->
                tasbeeh.groupId == id
            }
        }

    }

    override fun getTasbeehById(id: Long): Flow<TasbeehModel> {
        return flow.map { list ->
            list.first {
                tasbeeh.id == id
            }
        }
    }


    override suspend fun getTasbeehsCount(): Int = tasbeehsMap.size

    override suspend fun insertDefaultTasbeehs(
        tasbeehs: List<TasbeehModel>,
    ): EmptyDataResult<DataError> {
        return if (throwException) {
            AppResult.Error(DataError.Local.UNKNOWN)
        } else {
            for (tasbeh in tasbeehs) {
                tasbeehsMap[tasbeh.id!!] = tasbeeh
            }
            AppResult.Done(Unit)
        }
    }


}