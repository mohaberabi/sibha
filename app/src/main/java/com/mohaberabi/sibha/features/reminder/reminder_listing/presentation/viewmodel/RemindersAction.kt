package com.mohaberabi.sibha.features.reminder.reminder_listing.presentation.viewmodel

import com.mohaberabi.sibha.core.domain.model.ReminderModel

sealed interface RemindersAction {


    data object OnGoBackClick : RemindersAction
    data object OnAddReminder : RemindersAction

    data class OnDeleteReminder(val id: Long) : RemindersAction

    data class OnUpdateReminder(val reminder: ReminderModel) : RemindersAction
}