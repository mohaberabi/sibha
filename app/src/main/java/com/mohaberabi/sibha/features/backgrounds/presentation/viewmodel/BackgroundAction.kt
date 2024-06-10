package com.mohaberabi.sibha.features.backgrounds.presentation.viewmodel

import com.mohaberabi.sibha.core.util.const.SibhaBackgrounds

sealed interface BackgroundAction {


    data class OnBackGroundClick(val bg: SibhaBackgrounds) : BackgroundAction
    data object OnBackClick : BackgroundAction

    data class OnImagePicked(val bytes: ByteArray?) : BackgroundAction
}