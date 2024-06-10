package com.mohaberabi.sibha.features.notify_count.presentation.viewmodel

import com.mohaberabi.sibha.core.util.UiText


sealed interface NotifyCountEvent {
    data class Error(val error: UiText) : NotifyCountEvent
    data object Saved : NotifyCountEvent
    data object Deleted : NotifyCountEvent
}