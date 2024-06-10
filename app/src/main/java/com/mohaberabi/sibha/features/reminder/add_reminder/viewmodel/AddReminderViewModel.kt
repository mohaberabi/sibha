package com.mohaberabi.sibha.features.reminder.add_reminder.viewmodel

import android.os.SystemClock
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohaberabi.sibha.core.domain.model.ReminderModel
import com.mohaberabi.sibha.core.domain.reminder.SibhaInterval
import com.mohaberabi.sibha.core.domain.reminder.SibhaReminder
import com.mohaberabi.sibha.core.domain.reminder.SibhaReminderData
import com.mohaberabi.sibha.core.domain.repository.ReminderRepository
import com.mohaberabi.sibha.core.util.AppResult
import com.mohaberabi.sibha.core.util.asUiText
import com.mohaberabi.sibha.features.reminder.navigation.AddReminderRoute
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddReminderViewModel(
    private val reminderRepository: ReminderRepository,
    private val sibhaReminder: SibhaReminder,
) : ViewModel() {


    val _event = Channel<AddReminderEvent>()
    val event = _event.receiveAsFlow()


    val state = MutableStateFlow(AddReminderState())


    fun onAction(action: AddReminderAction) {
        when (action) {
            is AddReminderAction.OnBodyChanged -> state.update { it.copy(body = action.value) }
            AddReminderAction.OnSaveReminder -> saveReminder()
            is AddReminderAction.OnTimeChanged -> state.update { it.copy(choosedTime = action.time) }
            is AddReminderAction.OnTitleChanged -> state.update { it.copy(title = action.value) }
            else -> Unit
        }

    }

    private fun saveReminder() {

        viewModelScope.launch {

            val reminder = ReminderModel(
                id = null,
                title = state.value.title,
                body = state.value.body,
                hour = state.value.choosedTime.hour,
                minute = state.value.choosedTime.minute
            )
            when (val reminderRes = reminderRepository.createReminder(reminder)) {
                is AppResult.Done -> {
                    sibhaReminder.schaduleAndRepeatReminder(
                        startAtMillis = reminder.timeAsMillis(),
                        repeat = 1,
                        every = SibhaInterval.MINUTE,
                        data = SibhaReminderData(
                            reminder.title,
                            reminder.body,
                            reminderRes.data.toInt()
                        ),
                        recieverId = SibhaReminder.INTERVAL_REMINDER_RECEIEVER,
                    )
                    _event.send(AddReminderEvent.Added)
                }

                is AppResult.Error -> _event.send(AddReminderEvent.Error(reminderRes.error.asUiText()))
            
            }
        }
    }

}

