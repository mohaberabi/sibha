package com.mohaberabi.sibha.features.reminder.di

import com.mohaberabi.sibha.features.reminder.add_reminder.viewmodel.AddReminderViewModel
import com.mohaberabi.sibha.features.reminder.reminder_listing.presentation.viewmodel.RemindersViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module


val reminderModule = module {

    viewModelOf(::AddReminderViewModel)
    viewModelOf(::RemindersViewModel)
}