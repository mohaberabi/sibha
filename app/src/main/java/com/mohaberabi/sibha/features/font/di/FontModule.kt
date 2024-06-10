package com.mohaberabi.sibha.features.font.di

import com.mohaberabi.sibha.features.font.font_size.viewmodel.FontSizeViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module


val fontModule = module {
    viewModelOf(::FontSizeViewModel)
}