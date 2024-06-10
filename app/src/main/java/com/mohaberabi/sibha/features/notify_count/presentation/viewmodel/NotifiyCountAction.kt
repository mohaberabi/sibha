package com.mohaberabi.sibha.features.notify_count.presentation.viewmodel

sealed interface NotifiyCountAction {


    data object ToggleAddDialog : NotifiyCountAction
    data object OnBackClick : NotifiyCountAction
    data class OnCountChanged(val count: Int) : NotifiyCountAction
    data object OnSaveCount : NotifiyCountAction
    data class OnCountDelete(val count: Int) : NotifiyCountAction
}