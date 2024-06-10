package com.mohaberabi.sibha.features.reminder.reminder_listing.presentation.viewmodel

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.mohaberabi.sibha.util.MainCoroutineDispatcherExtension
import com.mohaberabi.sibha.util.fakes.reminder.FakeSibhaReminder
import com.mohaberabi.sibha.util.fakes.repository.FakeReminderRepository
import com.mohaberabi.sibha.util.generator.fakeReminderModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MainCoroutineDispatcherExtension::class)
class RemindersViewModelTest {
    private lateinit var sibhaReminder: FakeSibhaReminder
    private lateinit var reminderRepository: FakeReminderRepository

    private lateinit var remindersViewModel: RemindersViewModel


    @BeforeEach
    fun setup() {
        sibhaReminder = FakeSibhaReminder()
        reminderRepository = FakeReminderRepository()
        remindersViewModel = RemindersViewModel(reminderRepository, sibhaReminder)
    }


    @Test
    fun ` when onActiton is OnDeleteReminder() should delete from the repo , return done, fired once `() =
        runTest {

            remindersViewModel.onAction(RemindersAction.OnDeleteReminder(0L))
            advanceUntilIdle()
            assertThat(reminderRepository.called).isEqualTo(1)
            assertThat(sibhaReminder.called).isEqualTo(1)
            assertThat(sibhaReminder.reminders.contains(0L.toInt())).isEqualTo(false)
            assertThat(reminderRepository.reminders.containsKey(0L)).isEqualTo(false)

        }

    @Test
    fun ` when onActiton is OnDeleteReminder() should  never delete from the repo , return done, fired once `() =
        runTest {

            reminderRepository.returnError = true
            remindersViewModel.onAction(RemindersAction.OnDeleteReminder(0L))
            advanceUntilIdle()
            assertThat(reminderRepository.called).isEqualTo(1)
            assertThat(reminderRepository.reminders.containsKey(0L)).isEqualTo(true)
            assertThat(sibhaReminder.called).isEqualTo(0)
            assertThat(sibhaReminder.reminders.contains(0L.toInt())).isEqualTo(false)
        }

    @Test
    fun `when onAction is OnUpdateReminder() & reminder is enabled , updates repo , saves to reminder , returns done `() =
        runTest {

            remindersViewModel.onAction(
                RemindersAction.OnUpdateReminder(
                    fakeReminderModel(
                        id = 2L,
                        enabled = true
                    )
                )
            )
            advanceUntilIdle()
            assertThat(reminderRepository.called).isEqualTo(1)
            assertThat(reminderRepository.reminders[2L]!!.second).isEqualTo(true)
            assertThat(sibhaReminder.called).isEqualTo(1)
            assertThat(sibhaReminder.reminders.contains(2)).isEqualTo(true)
        }

    @Test
    fun `when onAction is OnUpdateReminder() & reminder is !enabled , never updates repo , removes from reminder , returns done `() =
        runTest {

            remindersViewModel.onAction(
                RemindersAction.OnUpdateReminder(
                    fakeReminderModel(
                        id = 0L,
                        enabled = false
                    )
                )
            )
            advanceUntilIdle()
            assertThat(reminderRepository.called).isEqualTo(1)
            assertThat(reminderRepository.reminders[0L]!!.second).isEqualTo(false)
            assertThat(sibhaReminder.called).isEqualTo(1)
            assertThat(sibhaReminder.reminders.contains(0)).isEqualTo(false)
        }

    @Test
    fun `when onAction is OnUpdateReminder() never updates repository nor reminder  , returns error send error event  `() =
        runTest {

            reminderRepository.returnError = true
            remindersViewModel.onAction(
                RemindersAction.OnUpdateReminder(
                    fakeReminderModel(
                        id = 0L,
                        enabled = false
                    )
                )
            )
            advanceUntilIdle()
            assertThat(reminderRepository.called).isEqualTo(1)
            assertThat(reminderRepository.reminders[0L]!!.second).isEqualTo(true)
            assertThat(sibhaReminder.called).isEqualTo(0)
            assertThat(sibhaReminder.reminders.contains(0)).isEqualTo(true)
            assertThat(remindersViewModel.event.first()).isInstanceOf(ReminderEvent::class.java)
        }


    @Test
    fun ` getAllRemidenrs() emits correct values `() = runTest {
        remindersViewModel.state.test {
            awaitItem()
            val emit1 = awaitItem()
            assertThat(emit1.reminders.size).isEqualTo(reminderRepository.reminders.size)
        }
    }

}