package com.mohaberabi.sibha.features.tasbeeh.presentation.viewmodel

import com.mohaberabi.sibha.core.util.UiText


sealed interface TasbeehEvent {
    data class Error(val error: UiText) : TasbeehEvent
    data object Added : TasbeehEvent
    data object Deleted : TasbeehEvent
    data object Reset : TasbeehEvent
}