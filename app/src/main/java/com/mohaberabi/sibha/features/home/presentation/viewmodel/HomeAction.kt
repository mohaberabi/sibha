package com.mohaberabi.sibha.features.home.presentation.viewmodel

import com.mohaberabi.sibha.core.domain.model.TasbeehModel


sealed interface HomeAction {
    data object OnIncrementTasbeeh : HomeAction
    data object OnToggleVibration : HomeAction
    data object OnResetTasbeeh : HomeAction
    data object OnToggleSound : HomeAction

    data object OnShareClick : HomeAction
    data object OnSettingsClick : HomeAction
    data object DismissRationalDialog : HomeAction

    data class OnTasbeehClick(val tasbeeh: TasbeehModel) : HomeAction
    data class SubmitNotificaitonPermission(
        val isGranted: Boolean,
        val showRational: Boolean,
    ) : HomeAction
}