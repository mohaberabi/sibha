package com.mohaberabi.sibha.util.fakes.source

import com.mohaberabi.sibha.core.database.dao.TasbeehDao
import com.mohaberabi.sibha.core.database.entity.TasbeehEntity
import com.mohaberabi.sibha.util.generator.fakeTasbeehEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class FakeTasbeehDao : TasbeehDao {


    var id = 0L
    var throwException: Boolean = false

    var tasbeehMap = mutableMapOf<Long, TasbeehEntity>()
    var flow = MutableStateFlow(
        listOf<TasbeehEntity>()
    )

    override suspend fun addTasbeeh(tasbeeh: TasbeehEntity) {

        if (throwException) {
            throw Exception()
        } else {
            tasbeehMap[id] = tasbeeh
            id++
        }
    }

    override suspend fun deleteTasbeeh(id: Long) {
        tasbeehMap.remove(id)
    }

    override suspend fun updateTasbeehCount(tasbeehId: Long, count: Int) {
        if (throwException) {
            throw Exception()
        } else {
            tasbeehMap[tasbeehId] = tasbeehMap[tasbeehId]!!.copy(count = count)

        }
    }

    override fun getAllTasbeeh(): Flow<List<TasbeehEntity>> {
        flow.value = listOf(
            fakeTasbeehEntity(id = 0),
            fakeTasbeehEntity(id = 1)
        )
        return flow
    }

    override fun getAllTasbeehByGroupId(id: Long): Flow<List<TasbeehEntity>> {
        flow.value = listOf(
            fakeTasbeehEntity(id = 0, groupId = 10L),
            fakeTasbeehEntity(id = 1, groupId = 10L)
        )
        return flow.map { list ->
            list.filter { it.groupId == id }
        }

    }

    override fun getTasbeehById(id: Long): Flow<TasbeehEntity> {
        flow.value = listOf(
            fakeTasbeehEntity(id = 0),
            fakeTasbeehEntity(id = 1)
        )
        return flow.map { list ->
            list.first { it.groupId == id }
        }
    }

    override suspend fun getTasbeehsCount(): Int = tasbeehMap.size

    override suspend fun insertDefaultTasbeehs(tasbeehs: List<TasbeehEntity>) {

        for (i in tasbeehs.indices) {
            tasbeehMap[i.toLong()] = tasbeehs[i].copy(id = i.toLong())

        }
    }

}