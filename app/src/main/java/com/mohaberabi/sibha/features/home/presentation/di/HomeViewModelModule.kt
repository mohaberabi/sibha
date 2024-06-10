package com.mohaberabi.sibha.features.home.presentation.di

import com.mohaberabi.sibha.features.home.presentation.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module


val homeViewModelModule = module {


    viewModelOf(::HomeViewModel)

}