package com.mohaberabi.sibha.features.reminder.reminder_listing.presentation.viewmodel

import com.mohaberabi.sibha.core.domain.model.ReminderModel

data class ReminderState(
    val reminders: List<ReminderModel> = listOf()
)
