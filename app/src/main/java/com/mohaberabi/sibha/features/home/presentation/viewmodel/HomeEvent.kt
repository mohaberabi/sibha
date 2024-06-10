package com.mohaberabi.sibha.features.home.presentation.viewmodel

import android.graphics.Bitmap
import com.mohaberabi.sibha.core.util.UiText

sealed interface HomeEvent {
    data class Error(val error: UiText) : HomeEvent
    data class ReadyToShareImage(val bitmap: Bitmap) : HomeEvent
}