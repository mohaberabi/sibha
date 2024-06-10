package com.mohaberabi.sibha.features.reminder.reminder_listing.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohaberabi.sibha.core.domain.model.ReminderModel
import com.mohaberabi.sibha.core.domain.model.toReminderData
import com.mohaberabi.sibha.core.domain.reminder.SibhaReminder
import com.mohaberabi.sibha.core.domain.repository.ReminderRepository
import com.mohaberabi.sibha.core.util.AppResult
import com.mohaberabi.sibha.core.util.asUiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RemindersViewModel(
    private val reminderRepository: ReminderRepository,
    private val sibhaReminder: SibhaReminder
) : ViewModel() {


    private val _event = Channel<ReminderEvent>()
    val event = _event.receiveAsFlow()

    val state = reminderRepository.getAllReminders()
        .map { ReminderState(reminders = it) }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            ReminderState()
        )

    fun onAction(action: RemindersAction) {

        when (action) {
            is RemindersAction.OnDeleteReminder -> deleteReminder(action.id)
            is RemindersAction.OnUpdateReminder -> enableDisableReminder(action.reminder)
            else -> Unit
        }
    }


    private fun deleteReminder(id: Long) {
        viewModelScope.launch {
            when (val res = reminderRepository.deleteReminder(id)) {
                is AppResult.Done -> {
                    sibhaReminder.cancel(id.toInt(), SibhaReminder.INTERVAL_REMINDER_RECEIEVER)
                }

                is AppResult.Error -> _event.send(ReminderEvent.Error(res.error.asUiText()))
            }
        }
    }

    private fun enableDisableReminder(
        reminder: ReminderModel
    ) {
        viewModelScope.launch {
            when (val res = reminderRepository.updateReminder(reminder.id!!, reminder.enabled)) {
                is AppResult.Done -> {
                    if (reminder.enabled) {
                        sibhaReminder.schaduleAndRepeatReminder(
                            startAtMillis = reminder.timeAsMillis(),
                            data = reminder.toReminderData,
                            recieverId = SibhaReminder.INTERVAL_REMINDER_RECEIEVER
                        )
                    } else {
                        sibhaReminder.cancel(
                            reminder.id.toInt(),
                            SibhaReminder.INTERVAL_REMINDER_RECEIEVER
                        )
                    }
                }

                is AppResult.Error -> _event.send(ReminderEvent.Error(res.error.asUiText()))
            }
        }

    }
}