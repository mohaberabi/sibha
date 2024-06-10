package com.mohaberabi.sibha.features.notify_count.di

import com.mohaberabi.sibha.features.notify_count.presentation.viewmodel.NotifyCountViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module


val notifyCountModule = module {

    viewModelOf(::NotifyCountViewModel)
}