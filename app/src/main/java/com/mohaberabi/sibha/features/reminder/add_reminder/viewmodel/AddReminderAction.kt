package com.mohaberabi.sibha.features.reminder.add_reminder.viewmodel

import com.mohaberabi.sibha.core.presentation.compose.SibhaTimePickerResult


sealed interface AddReminderAction {
    data class OnTitleChanged(val value: String) : AddReminderAction

    data class OnBodyChanged(val value: String) : AddReminderAction
    data class OnTimeChanged(val time: SibhaTimePickerResult) : AddReminderAction
    data object OnSaveReminder : AddReminderAction
    data object OnGoBackClick : AddReminderAction

}