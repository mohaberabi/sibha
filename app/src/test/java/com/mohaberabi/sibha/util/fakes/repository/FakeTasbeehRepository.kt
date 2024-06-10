package com.mohaberabi.sibha.util.fakes.repository

import com.mohaberabi.sibha.core.domain.model.TasbeehModel
import com.mohaberabi.sibha.core.domain.repository.TasbeehRepository
import com.mohaberabi.sibha.core.util.AppResult
import com.mohaberabi.sibha.core.util.DataError
import com.mohaberabi.sibha.core.util.EmptyDataResult
import com.mohaberabi.sibha.core.util.emptyDoneResult
import com.mohaberabi.sibha.util.generator.fakeTasbeehModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update

class FakeTasbeehRepository : TasbeehRepository {


    var called: Int = 0
        private set


    var mapTop = 2L
        private set
    var tasbeehsMap = mutableMapOf<Long, TasbeehModel>(
        0L to fakeTasbeehModel(id = 0L, count = 100),
        1L to fakeTasbeehModel(id = 1L),
        2L to fakeTasbeehModel(id = 2L),
    )
        private set

    private val flow = MutableStateFlow(listOf<TasbeehModel>())
    var returnError: Boolean = false
    private val error = AppResult.Error(DataError.Local.UNKNOWN)
    override suspend fun addTasbeeh(
        tasbeeh: TasbeehModel,
    ): EmptyDataResult<DataError.Local> {
        called++
        return if (returnError) {
            error
        } else {
            mapTop++
            tasbeehsMap[mapTop] = tasbeeh.copy(id = mapTop)
            emptyDoneResult
        }
    }

    override suspend fun deleteTasbeeh(
        id: Long,
    ) {
        called++
        tasbeehsMap.remove(id)
    }


    override suspend fun updateTasbeehCount(
        tasbeehId: Long,
        count: Int
    ): EmptyDataResult<DataError.Local> {
        called++
        return if (returnError) {
            error
        } else {
            tasbeehsMap[tasbeehId] = tasbeehsMap[tasbeehId]!!.copy(count = count)
            emptyDoneResult
        }

    }

    override fun getAllTasbeeh(): Flow<List<TasbeehModel>> {
        return flow.also {
            it.update {
                tasbeehsMap.values.toList()
            }
        }
    }

    override fun getAllTasbeehByGroupId(id: Long): Flow<List<TasbeehModel>> = flowOf()

    override fun getTasbeehById(id: Long): Flow<TasbeehModel> = flowOf()

    override suspend fun addDefaultTasbeehs(
        tasbeeehs: List<TasbeehModel>,
    ): EmptyDataResult<DataError> {
        called++
        return if (returnError) {
            error
        } else {
            for (i in tasbeeehs.indices) {
                tasbeehsMap[i.toLong()] = tasbeeehs[i].copy(id = i.toLong())
            }

            emptyDoneResult
        }
    }

    override suspend fun getTasbeehsCount(): Int {
        called++
        return tasbeehsMap.size
    }

}