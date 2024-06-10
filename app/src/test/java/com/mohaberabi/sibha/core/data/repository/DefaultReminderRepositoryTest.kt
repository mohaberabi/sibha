package com.mohaberabi.sibha.core.data.repository

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.mohaberabi.sibha.core.domain.model.ReminderModel
import com.mohaberabi.sibha.util.fakes.source.FakeRemidnerLocalDataSource
import com.mohaberabi.sibha.core.util.AppResult
import com.mohaberabi.sibha.core.util.DataError
import com.mohaberabi.sibha.util.MainCoroutineDispatcherExtension
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith


@ExtendWith(MainCoroutineDispatcherExtension::class)
class DefaultReminderRepositoryTest {


    private lateinit var reminderRepository: DefaultReminderRepository


    private lateinit var reminderLocalDataSource: FakeRemidnerLocalDataSource


    @BeforeEach
    fun setup() {
        reminderLocalDataSource = FakeRemidnerLocalDataSource()
        reminderRepository = DefaultReminderRepository(reminderLocalDataSource)
    }

    private val reminder = ReminderModel(null, "", "", 1, 1)

    @Test
    fun `when create reminder saves to local and returns done with new id `() = runTest {


        val res = reminderRepository.createReminder(reminder)


        assertThat(res).isEqualTo(AppResult.Done(3L))

        assertThat(reminderLocalDataSource.reminderMap.containsKey(3L))

    }


    @Test
    fun `when create reminder never  saves to local and returns error `() = runTest {

        reminderLocalDataSource.throwException = true

        val res = reminderRepository.createReminder(reminder)


        assertThat(res).isEqualTo(AppResult.Error(DataError.Local.UNKNOWN))

        assertThat(reminderLocalDataSource.reminderMap.containsKey(3L)).isFalse()

    }

    @Test
    fun `when delete  reminder it is removed from local datasource and returns done `() = runTest {


        val res = reminderRepository.deleteReminder(1L)


        assertThat(res).isEqualTo(AppResult.Done(Unit))

        assertThat(reminderLocalDataSource.reminderMap.containsKey(1L)).isFalse()

    }


    @Test
    fun `when delete  reminder it is never  removed from local datasource and returns error `() =
        runTest {


            reminderLocalDataSource.throwException = true

            val res = reminderRepository.deleteReminder(1L)


            assertThat(res).isEqualTo(AppResult.Error(DataError.Local.UNKNOWN))

            assertThat(reminderLocalDataSource.reminderMap.containsKey(1L)).isTrue()

        }


    @Test
    fun `when update  reminder it is updated in local datasource and returns done  `() =
        runTest {


            val res = reminderRepository.updateReminder(1L, false)


            assertThat(res).isEqualTo(AppResult.Done(Unit))

            assertThat(reminderLocalDataSource.reminderMap.containsKey(1L)).isTrue()
            assertThat(reminderLocalDataSource.reminderMap[1L]!!.enabled).isFalse()


        }


    @Test
    fun `when update  reminder it is never  updated in local datasource and returns error   `() =
        runTest {
            reminderLocalDataSource.throwException = true
            val res = reminderRepository.updateReminder(1L, false)
            assertThat(res).isEqualTo(AppResult.Error(DataError.Local.UNKNOWN))
            assertThat(reminderLocalDataSource.reminderMap.containsKey(1L)).isTrue()
            assertThat(reminderLocalDataSource.reminderMap[1L]!!.enabled).isTrue()
        }


    @Test
    fun `test get reminders flow `() = runTest {


        reminderRepository.getAllReminders().test {
            val emit1 = awaitItem()
            assertThat(emit1[0].id).isEqualTo(1L)
            assertThat(emit1[1].id).isEqualTo(2L)

        }

    }


}