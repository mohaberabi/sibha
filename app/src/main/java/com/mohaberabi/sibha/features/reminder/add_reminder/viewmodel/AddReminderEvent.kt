package com.mohaberabi.sibha.features.reminder.add_reminder.viewmodel

import com.mohaberabi.sibha.core.util.UiText

sealed interface AddReminderEvent {


    data class Error(val error: UiText) : AddReminderEvent
    data object Added : AddReminderEvent
}