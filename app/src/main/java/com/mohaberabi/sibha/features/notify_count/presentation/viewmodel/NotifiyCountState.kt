package com.mohaberabi.sibha.features.notify_count.presentation.viewmodel

data class NotifiyCountState(
    val notifiyCount: Set<Int> = setOf(),
    val loading: Boolean = false,
    val addDialogShown: Boolean = false,
    val count: Int = 33
)