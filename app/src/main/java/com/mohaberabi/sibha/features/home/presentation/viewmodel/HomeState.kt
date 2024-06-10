package com.mohaberabi.sibha.features.home.presentation.viewmodel

import com.mohaberabi.sibha.core.domain.model.TasbeehModel
import com.mohaberabi.sibha.core.domain.model.UserPrefsModel

data class HomeState(
    val playSound: Boolean = true,
    val vibrate: Boolean = true,
    val tasbeeh: TasbeehModel? = null,
    val tasbeehs: List<TasbeehModel> = listOf(),
    val user: UserPrefsModel = UserPrefsModel(),
    val showNotificationRational: Boolean = false,
    val notificaitonGranted: Boolean = false,
)
