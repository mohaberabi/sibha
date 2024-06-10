package com.mohaberabi.sibha.features.reminder.add_reminder.viewmodel

import com.mohaberabi.sibha.core.presentation.compose.SibhaTimePickerResult

data class AddReminderState(
    val isLoading: Boolean = false,
    val title: String = "",
    val body: String = "",
    val choosedTime: SibhaTimePickerResult = SibhaTimePickerResult()
)


val AddReminderState.canAdd: Boolean
    get() = title.isNotEmpty() && body.isNotEmpty()