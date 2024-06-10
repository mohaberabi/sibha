package com.mohaberabi.sibha.features.backgrounds.presentation.viewmodel

import com.mohaberabi.sibha.core.util.UiText

sealed interface BackgroundEvent {

    data class Error(val error: UiText) : BackgroundEvent

    data object ChangedBg : BackgroundEvent
}