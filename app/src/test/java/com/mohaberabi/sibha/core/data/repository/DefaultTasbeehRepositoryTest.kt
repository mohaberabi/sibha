package com.mohaberabi.sibha.core.data.repository

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.mohaberabi.sibha.core.domain.model.TasbeehModel
import com.mohaberabi.sibha.util.fakes.source.FakeTasbeehLocalDataSource
import com.mohaberabi.sibha.core.util.emptyDoneResult
import com.mohaberabi.sibha.util.MainCoroutineDispatcherExtension
import com.mohaberabi.sibha.util.unKnwonLocalError
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith


@ExtendWith(MainCoroutineDispatcherExtension::class)
class DefaultTasbeehRepositoryTest {

    private lateinit var tasbeehRepository: DefaultTasbeehRepository

    private lateinit var tasbeehLocalDataSource: FakeTasbeehLocalDataSource


    @BeforeEach
    fun setup() {
        tasbeehLocalDataSource = FakeTasbeehLocalDataSource()
        tasbeehRepository = DefaultTasbeehRepository(tasbeehLocalDataSource)
    }


    @Test
    fun `when add tasbeeh saved to local source and return done `() = runTest {
        val tsbeeh = TasbeehModel(null, "tasbeeh", null, 1)
        val res = tasbeehRepository.addTasbeeh(tsbeeh)
        assertThat(res).isEqualTo(emptyDoneResult)
        assertThat(tasbeehLocalDataSource.tasbeehsMap.containsKey(6L))

    }


    @Test
    fun `when add tasbeeh never  saved to local source and return error  `() = runTest {
        tasbeehLocalDataSource.throwException = true
        val tsbeeh = TasbeehModel(null, "tasbeeh", null, 1)
        val res = tasbeehRepository.addTasbeeh(tsbeeh)
        assertThat(res).isEqualTo(unKnwonLocalError)
        assertThat(tasbeehLocalDataSource.tasbeehsMap.containsKey(6L)).isFalse()

    }

    @Test
    fun `when delete tasbeeh removed from local return done `() = runTest {
        val res = tasbeehRepository.deleteTasbeeh(3L)
        assertThat(tasbeehLocalDataSource.tasbeehsMap.containsKey(3L)).isFalse()

    }

    @Test
    fun `when updateTasbeehCount  tasbeeh count incremented in local and returns done  `() =
        runTest {
            val res = tasbeehRepository.updateTasbeehCount(3L, 100)
            assertThat(tasbeehLocalDataSource.tasbeehsMap[3L]!!.count).isEqualTo(100)

            assertThat(res).isEqualTo(emptyDoneResult)
        }

    @Test
    fun `when updateTasbeehCount  tasbeeh count never  incremented in local and returns error   `() =
        runTest {
            tasbeehLocalDataSource.throwException = true
            val res = tasbeehRepository.updateTasbeehCount(3L, 100)
            assertThat(tasbeehLocalDataSource.tasbeehsMap[3L]!!.count).isEqualTo(0)
            assertThat(res).isEqualTo(unKnwonLocalError)
        }


    @Test
    fun `test getting all tasbeehs`() = runTest {
        tasbeehRepository.getAllTasbeeh().test {

            val emit = awaitItem()

            assertThat(emit[0].id).isEqualTo(1L)
            assertThat(emit[1].id).isEqualTo(2L)
            assertThat(emit[2].id).isEqualTo(3L)
            assertThat(emit[3].id).isEqualTo(4L)

        }
    }


    @Test
    fun `test getting all tasbeehs by groupid`() = runTest {
        tasbeehRepository.getAllTasbeehByGroupId(1000L).test {

            val list = awaitItem()

            assertThat(list.all { it.groupId == 1000L }).isTrue()
        }

    }


    @Test
    fun ` when get tasbeeh count  returns size exist in localDatasource`() = runTest {

        val res = tasbeehRepository.getTasbeehsCount()
        assertThat(res).isEqualTo(tasbeehLocalDataSource.tasbeehsMap.size)

    }

    @Test
    fun `get tasbeeh by id `() = runTest {

        tasbeehRepository.getTasbeehById(1L).test {
            val emit1 = awaitItem()

            assertThat(emit1.id).isEqualTo(1L)
        }
    }


}