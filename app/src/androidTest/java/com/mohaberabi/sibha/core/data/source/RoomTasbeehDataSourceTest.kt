package com.mohaberabi.sibha.core.data.source

import com.google.common.truth.Truth.assertThat
import com.mohaberabi.sibha.core.database.dao.TasbeehDao
import com.mohaberabi.sibha.core.domain.model.TasbeehModel
import com.mohaberabi.sibha.core.util.AppResult
import com.mohaberabi.sibha.util.AndroidTestKoinRule
import com.mohaberabi.sibha.util.MainCoroutineDispatcherExtension
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject


class RoomTasbeehDataSourceTest : KoinTest {


    val module = module {

        single {
            RoomTasbeehDataSource(get())
        }
    }

    @get:Rule
    val androidRule = AndroidTestKoinRule(modules = listOf(module))

    @get:Rule
    val mainDispatcherRule = MainCoroutineDispatcherExtension()
    val tasbeehDataSource: RoomTasbeehDataSource by inject()
    val tasbeehDao: TasbeehDao by inject()
    val tasbeeh = TasbeehModel(null, "test", null)

    @Test
    fun addTasbeehAddToDataBase() = runTest {

        val res = tasbeehDataSource.addTasbeeh(tasbeeh)
        assertThat(res).isEqualTo(AppResult.Done(Unit))
        val found = tasbeehDao.getAllTasbeeh().first()[0]
        assertThat(found.id).isEqualTo(1L)
        assertThat(found.tasbeeh).isEqualTo("test")

    }


    @Test

    fun deleteTasbeehDeletesFromDatabase() = runTest {
        val res = tasbeehDataSource.addTasbeeh(tasbeeh)
        assertThat(res).isEqualTo(AppResult.Done(Unit))
        val length = tasbeehDao.getTasbeehsCount()
        assertThat(length).isEqualTo(1)
        tasbeehDataSource.deleteTasbeeh(1L)
        val lengthAfter = tasbeehDao.getTasbeehsCount()
        assertThat(lengthAfter).isEqualTo(0)
    }
}