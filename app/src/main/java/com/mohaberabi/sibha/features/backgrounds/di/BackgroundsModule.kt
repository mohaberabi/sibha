package com.mohaberabi.sibha.features.backgrounds.di

import com.mohaberabi.sibha.features.backgrounds.presentation.viewmodel.BackgroundsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module


val backgroundsModule = module {

    viewModelOf(::BackgroundsViewModel)
}