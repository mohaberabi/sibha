package com.mohaberabi.sibha.features.tasbeeh.presentation.viewmodel


sealed interface TasbeehAction {


    data class OnDeleteTasbeeh(val id: Long) : TasbeehAction

    data class OnResetTasbeeh(val id: Long) : TasbeehAction

    data class OnTasbeehTextChanged(val text: String) : TasbeehAction

    data object OnToggleAddSheet : TasbeehAction
    data object OnBackClick : TasbeehAction
    data object OnSaveAddedTasbeh : TasbeehAction

}