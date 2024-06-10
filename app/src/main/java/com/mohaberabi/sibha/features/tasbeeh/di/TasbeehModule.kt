package com.mohaberabi.sibha.features.tasbeeh.di

import com.mohaberabi.sibha.features.tasbeeh.presentation.viewmodel.TasbeehViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module


val tasbeehModule = module {
    viewModelOf(::TasbeehViewModel)
}