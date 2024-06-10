package com.mohaberabi.sibha.features.notify_count.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohaberabi.sibha.core.domain.repository.UserRepository
import com.mohaberabi.sibha.core.util.AppResult
import com.mohaberabi.sibha.core.util.asUiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class NotifyCountViewModel(
    private val userRepository: UserRepository,
) : ViewModel() {


    private val _event = Channel<NotifyCountEvent>()
    val event = _event.receiveAsFlow()

    val _state = MutableStateFlow(NotifiyCountState())

    val state = _state.asStateFlow()

    init {
        userRepository.getUserData().onEach { user ->
            _state.update {
                it.copy(notifiyCount = user.notifyCheckpoints)
            }
        }.launchIn(
            viewModelScope,
        )
    }


    fun onAction(action: NotifiyCountAction) {
        when (action) {
            is NotifiyCountAction.OnCountChanged -> onCountChanged(action.count)
            is NotifiyCountAction.OnCountDelete -> deleteCount(action.count)
            NotifiyCountAction.OnSaveCount -> saveCount()
            NotifiyCountAction.ToggleAddDialog -> toggleDialog()
            else -> Unit
        }
    }


    private fun deleteCount(count: Int) {
        val set = _state.value.notifiyCount.toMutableSet()
            .apply {
                if (contains(count)) remove(count)
            }
        _state.update { it.copy(loading = true) }
        viewModelScope.launch {
            when (val res = userRepository.updateNotifyCheckpoint(set)) {
                is AppResult.Done -> _event.send(NotifyCountEvent.Deleted)
                is AppResult.Error -> _event.send(NotifyCountEvent.Error(res.error.asUiText()))
            }
        }
        _state.update { it.copy(loading = false) }

    }

    private fun onCountChanged(count: Int) = _state.update { it.copy(count = count) }


    private fun toggleDialog() = _state.update { it.copy(addDialogShown = !it.addDialogShown) }

    private fun saveCount() {
        val set = _state.value.notifiyCount.toMutableSet()
            .apply { add(_state.value.count) }
        _state.update { it.copy(loading = true) }
        viewModelScope.launch {
            when (val res = userRepository.updateNotifyCheckpoint(set)) {
                is AppResult.Done -> _event.send(NotifyCountEvent.Saved)
                is AppResult.Error -> _event.send(NotifyCountEvent.Error(res.error.asUiText()))
            }
        }
        _state.update { it.copy(loading = false, addDialogShown = false) }
    }
}