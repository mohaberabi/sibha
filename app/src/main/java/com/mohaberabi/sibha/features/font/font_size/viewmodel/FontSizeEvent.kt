package com.mohaberabi.sibha.features.font.font_size.viewmodel

import com.mohaberabi.sibha.core.util.UiText

sealed interface FontSizeEvent {


    data class Error(val error: UiText) : FontSizeEvent

    data object Saved : FontSizeEvent
}