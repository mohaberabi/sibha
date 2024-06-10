package com.mohaberabi.sibha.core.data.source

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.mohaberabi.sibha.core.util.AppResult
import com.mohaberabi.sibha.util.MainCoroutineDispatcherExtension
import com.mohaberabi.sibha.util.fakes.source.FakeTasbeehDao
import com.mohaberabi.sibha.util.generator.fakeTasbeehEntity
import com.mohaberabi.sibha.util.generator.fakeTasbeehModel
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith


@ExtendWith(MainCoroutineDispatcherExtension::class)
class RoomTasbeehDataSourceTest {
    private lateinit var tasbeehLocalDataSource: RoomTasbeehDataSource

    private lateinit var tasbeehDao: FakeTasbeehDao


    @BeforeEach
    fun setup() {

        tasbeehDao = FakeTasbeehDao()
        tasbeehLocalDataSource = RoomTasbeehDataSource(tasbeehDao)

    }


    @Test
    fun ` when insert tasbeeh adds to db and returns done `() = runTest {

        val res = tasbeehLocalDataSource.addTasbeeh(fakeTasbeehModel())

        assertThat(res).isInstanceOf(AppResult.Done::class.java)
        assertThat(tasbeehDao.tasbeehMap.containsKey(0L)).isTrue()
    }

    @Test
    fun ` when insert tasbeeh never  adds to db and returns error  `() = runTest {

        tasbeehDao.throwException = true
        val res = tasbeehLocalDataSource.addTasbeeh(fakeTasbeehModel())

        assertThat(res).isInstanceOf(AppResult.Error::class.java)
        assertThat(tasbeehDao.tasbeehMap.containsKey(0L)).isFalse()
    }

    @Test
    fun ` delete tasbeeh removes from db and `() = runTest {
        val res = tasbeehLocalDataSource.addTasbeeh(fakeTasbeehModel())
        assertThat(res).isInstanceOf(AppResult.Done::class.java)
        assertThat(tasbeehDao.tasbeehMap.containsKey(0L)).isTrue()
        tasbeehLocalDataSource.deleteTasbeeh(0L)
        assertThat(tasbeehDao.tasbeehMap.containsKey(0L)).isFalse()

    }

    @Test
    fun ` when updateTasbeehCount() update tasbeeh count on db and returns done `() = runTest {
        val res = tasbeehLocalDataSource.addTasbeeh(fakeTasbeehModel())

        assertThat(res).isInstanceOf(AppResult.Done::class.java)
        assertThat(tasbeehDao.tasbeehMap.containsKey(0L)).isTrue()

        val res2 = tasbeehLocalDataSource.updateTasbeehCount(0L, 100)

        assertThat(res2).isInstanceOf(AppResult.Done::class.java)
        assertThat(tasbeehDao.tasbeehMap[0L]!!.count).isEqualTo(100)

    }

    @Test
    fun ` when updateTasbeehCount() never  update tasbeeh count on db and returns error `() =
        runTest {
            val res = tasbeehLocalDataSource.addTasbeeh(fakeTasbeehModel())

            assertThat(res).isInstanceOf(AppResult.Done::class.java)
            assertThat(tasbeehDao.tasbeehMap.containsKey(0L)).isTrue()

            tasbeehDao.throwException = true
            val res2 = tasbeehLocalDataSource.updateTasbeehCount(0L, 100)

            assertThat(res2).isInstanceOf(AppResult.Error::class.java)
            assertThat(tasbeehDao.tasbeehMap[0L]!!.count).isEqualTo(0)

        }


    @Test
    fun ` getAllTasbeehs() emits correct values `() = runTest {


        tasbeehLocalDataSource.getAllTasbeeh().test {
            val emit1 = awaitItem()
            assertThat(emit1.size).isEqualTo(2)
            assertThat(emit1[0].id).isEqualTo(0L)
            assertThat(emit1[1].id).isEqualTo(1L)

        }
    }


    @Test
    fun ` when insertDefaultTasbeehs() all added to db and returns done `() = runTest {

        val res = tasbeehLocalDataSource.insertDefaultTasbeehs(
            listOf(
                fakeTasbeehModel(id = 0L),
                fakeTasbeehModel(id = 1L),
                fakeTasbeehModel(id = 2L)
            )
        )


        assertThat(res).isInstanceOf(AppResult.Done::class.java)
        assertThat(tasbeehDao.tasbeehMap.containsKey(0L)).isEqualTo(true)
        assertThat(tasbeehDao.tasbeehMap.containsKey(1L)).isEqualTo(true)
        assertThat(tasbeehDao.tasbeehMap.containsKey(2L)).isEqualTo(true)

    }

}