package com.mohaberabi.sibha.features.reminder.add_reminder.viewmodel

import com.google.common.truth.Truth.assertThat
import com.mohaberabi.sibha.util.MainCoroutineDispatcherExtension
import com.mohaberabi.sibha.util.fakes.reminder.FakeSibhaReminder
import com.mohaberabi.sibha.util.fakes.repository.FakeReminderRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

//@OptIn(ExperimentalCoroutinesApi::class)
//@ExtendWith(MainCoroutineDispatcherExtension::class)
//class AddReminderViewModelTest {
//    private lateinit var sibhaReminder: FakeSibhaReminder
//    private lateinit var reminderRepository: FakeReminderRepository
//    private lateinit var addReminderViewModel: AddReminderViewModel
//
//    @BeforeEach
//    fun setup() {
//
//        reminderRepository = FakeReminderRepository()
//        sibhaReminder = FakeSibhaReminder()
//        addReminderViewModel = AddReminderViewModel(reminderRepository, sibhaReminder)
//    }
//
//
//    @Test
//    fun ` when onAction() is OnSaveReminder() adds to repo saves remidner returns done `() =
//        runTest {
//            addReminderViewModel.onAction(AddReminderAction.OnSaveReminder)
//            advanceUntilIdle()
//            assertThat(addReminderViewModel.event.first()).isInstanceOf(AddReminderEvent.Added::class.java)
//        }
//}