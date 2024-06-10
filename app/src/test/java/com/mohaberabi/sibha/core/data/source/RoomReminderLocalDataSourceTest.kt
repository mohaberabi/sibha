package com.mohaberabi.sibha.core.data.source

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.mohaberabi.sibha.core.domain.model.ReminderModel
import com.mohaberabi.sibha.core.util.AppResult
import com.mohaberabi.sibha.core.util.DataError
import com.mohaberabi.sibha.util.MainCoroutineDispatcherExtension
import com.mohaberabi.sibha.util.fakes.dao.FakeReminderDao
import com.mohaberabi.sibha.util.generator.fakeReminderEntity
import com.mohaberabi.sibha.util.generator.fakeReminderModel
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MainCoroutineDispatcherExtension::class)
class RoomReminderLocalDataSourceTest {
    private lateinit var reminderDataSource: RoomReminderLocalDataSource


    private lateinit var reminderDao: FakeReminderDao


    @BeforeEach
    fun setup() {
        reminderDao = FakeReminderDao()
        reminderDataSource = RoomReminderLocalDataSource(reminderDao)
    }


    @Test
    fun `when datasource insertReminder adds to database and returns the new id done `() = runTest {

        val res = reminderDataSource.createReminder(fakeReminderModel())

        assertThat(res).isEqualTo(AppResult.Done(0L))

        assertThat(reminderDao.reminderMap).containsKey(0L)

    }


    @Test
    fun `when datasource insertReminder adds to database and returns the new id done mutliple `() =
        runTest {

            val res1 = reminderDataSource.createReminder(fakeReminderModel())
            val res2 = reminderDataSource.createReminder(fakeReminderModel())
            val res3 = reminderDataSource.createReminder(fakeReminderModel())

            assertThat(res1).isEqualTo(AppResult.Done(0L))
            assertThat(res2).isEqualTo(AppResult.Done(1L))
            assertThat(res3).isEqualTo(AppResult.Done(2L))


            assertThat(reminderDao.reminderMap).containsKey(0L)
            assertThat(reminderDao.reminderMap).containsKey(1L)
            assertThat(reminderDao.reminderMap).containsKey(2L)


        }

    @Test
    fun `when datasource insertReminder never  adds to database & return error`() =
        runTest {

            reminderDao.throwException = true
            val res1 = reminderDataSource.createReminder(fakeReminderModel())
            assertThat(res1).isEqualTo(AppResult.Error(DataError.Local.UNKNOWN))
            assertThat(reminderDao.reminderMap.containsKey(0L)).isFalse()


        }


    @Test
    fun `when datasource delete remidner removes it from datatabase `() = runTest {

        reminderDao.insertReminder(fakeReminderEntity())

        assertThat(reminderDao.reminderMap.containsKey(0L)).isEqualTo(true)

        reminderDataSource.deleteReminder(0L)
        assertThat(reminderDao.reminderMap.containsKey(0L)).isEqualTo(false)

    }


    @Test
    fun `when datasource update remidner it updates it in db `() = runTest {

        reminderDao.insertReminder(fakeReminderEntity())

        assertThat(reminderDao.reminderMap.containsKey(0L)).isEqualTo(true)

        reminderDataSource.updateReminder(0L, false)
        assertThat(reminderDao.reminderMap[0L]!!.enabled).isEqualTo(false)

    }


    @Test
    fun ` get all reminder emits correct values`() = runTest {

        reminderDataSource.getAllReminders().test {
            val emit1 = awaitItem()
            assertThat(emit1.size).isEqualTo(1)
            assertThat(emit1[0].id).isEqualTo(0L)
        }

    }
}