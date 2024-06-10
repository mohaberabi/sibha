package com.mohaberabi.sibha.features.tasbeeh.presentation.viewmodel

import com.mohaberabi.sibha.core.domain.model.TasbeehModel


data class TasbeehState(
    val tasbeehText: String = "",
    val tasbeehs: List<TasbeehModel> = listOf(),
    val loading: Boolean = false,
    val addSheetOpen: Boolean = false,

    )