package com.mohaberabi.sibha.features.font.font_size.viewmodel

sealed interface FontSizeAction {


    data class OnSaveFontSize(
        val size: Int
    ) : FontSizeAction

    data object OnGoBack : FontSizeAction
}