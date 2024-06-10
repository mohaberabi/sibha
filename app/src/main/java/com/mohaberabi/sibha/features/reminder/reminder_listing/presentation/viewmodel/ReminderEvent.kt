package com.mohaberabi.sibha.features.reminder.reminder_listing.presentation.viewmodel

import com.mohaberabi.sibha.core.util.UiText


sealed interface ReminderEvent {
    data class Error(val error: UiText) : ReminderEvent
}