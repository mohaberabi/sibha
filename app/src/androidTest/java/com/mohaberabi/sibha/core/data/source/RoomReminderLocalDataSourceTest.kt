package com.mohaberabi.sibha.core.data.source

import android.database.sqlite.SQLiteFullException
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.mohaberabi.sibha.core.database.dao.ReminderDao
import com.mohaberabi.sibha.core.domain.datasource.ReminderLocalDataSource
import com.mohaberabi.sibha.core.domain.model.ReminderModel
import com.mohaberabi.sibha.util.AndroidTestKoinRule
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent
import org.koin.java.KoinJavaComponent.inject
import org.koin.test.KoinTest
import org.koin.test.inject
import javax.inject.Inject
import com.google.common.truth.Truth.*
import com.mohaberabi.sibha.core.database.entity.toReminderEntity
import com.mohaberabi.sibha.core.util.AppResult
import com.mohaberabi.sibha.core.util.DataError
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

@RunWith(AndroidJUnit4::class)
class RoomReminderLocalDataSourceTest : KoinTest {

    private val reminder = ReminderModel(
        null,
        "title",
        "body",
        1,
        1,
        true,
    )

    @get:Rule
    val koinTestRule = AndroidTestKoinRule()


    private val reminderDao: ReminderDao by inject()
    private val roomReminderLocalDataSource: RoomReminderLocalDataSource by inject()


    @Test
    fun whenCreatesARemidnerItShouldBeSavedToDAO() = runTest {

        roomReminderLocalDataSource.createReminder(reminder)
        val found = reminderDao.getAllReminder().first()[0]
        assertThat(found.title).isEqualTo(reminder.title)
    }


    @Test
    fun whenDeleteReminderShouldRemovedFromDb() = runTest {
        roomReminderLocalDataSource.createReminder(reminder)
        val found = reminderDao.getAllReminder().first()[0]
        assertThat(found.title).isEqualTo(reminder.title)
        roomReminderLocalDataSource.deleteReminder(1L)
        val list = reminderDao.getAllReminder().first()
        assertThat(list.isEmpty()).isTrue()

    }


    @Test
    fun whenUpdateReminderShouldBeUpdatedOnDb() = runTest {
        roomReminderLocalDataSource.createReminder(reminder)
        val found = reminderDao.getAllReminder().first()[0]
        assertThat(found.title).isEqualTo(reminder.title)
        roomReminderLocalDataSource.updateReminder(1L, false)
        val afterUpdate = reminderDao.getAllReminder().first()[0]
        assertThat(afterUpdate.enabled).isFalse()

    }

    @Test
    fun emitsCorrectList() = runTest {
        roomReminderLocalDataSource.getAllReminders().test {
            val emit1 = awaitItem()
            assertThat(emit1.isEmpty()).isTrue()
            roomReminderLocalDataSource.createReminder(reminder)
            val emit2 = awaitItem()
            assertThat(emit2.size).isEqualTo(1)
            roomReminderLocalDataSource.createReminder(reminder)
            val emit3 = awaitItem()
            assertThat(emit3.size).isEqualTo(2)

        }
    }
}