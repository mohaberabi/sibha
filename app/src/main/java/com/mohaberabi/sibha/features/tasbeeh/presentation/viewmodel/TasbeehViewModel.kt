package com.mohaberabi.sibha.features.tasbeeh.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohaberabi.sibha.core.domain.model.TasbeehModel
import com.mohaberabi.sibha.core.domain.repository.TasbeehRepository
import com.mohaberabi.sibha.core.util.AppResult
import com.mohaberabi.sibha.core.util.asUiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class TasbeehViewModel(
    private val tasbeehRepository: TasbeehRepository,
) : ViewModel() {


    private val _event = Channel<TasbeehEvent>()
    val event = _event.receiveAsFlow()
    private val _state = MutableStateFlow(TasbeehState())
    val state = _state.asStateFlow()


    init {
        tasbeehRepository.getAllTasbeeh()
            .onEach { tasbehs -> _state.update { it.copy(tasbeehs = tasbehs) } }
            .launchIn(viewModelScope)
    }

    fun onAction(action: TasbeehAction) {
        when (action) {
            TasbeehAction.OnToggleAddSheet -> _state.update { it.copy(addSheetOpen = !it.addSheetOpen) }
            TasbeehAction.OnSaveAddedTasbeh -> addTasbeeh()
            TasbeehAction.OnBackClick -> {}
            is TasbeehAction.OnDeleteTasbeeh -> deleteTasbeeh(action.id)
            is TasbeehAction.OnResetTasbeeh -> resetTasbeeh(action.id)
            is TasbeehAction.OnTasbeehTextChanged -> onTasbeehTextChanged(action.text)
        
        }
    }

    private fun addTasbeeh() {
        _state.update { it.copy(loading = true) }

        viewModelScope.launch {
            when (val res =
                tasbeehRepository.addTasbeeh(TasbeehModel(tasbeeh = _state.value.tasbeehText))) {
                is AppResult.Done -> _event.send(TasbeehEvent.Added)
                is AppResult.Error -> _event.send(TasbeehEvent.Error(res.error.asUiText()))
            }
        }
        _state.update { it.copy(loading = false, addSheetOpen = false, tasbeehText = "") }

    }

    private fun deleteTasbeeh(id: Long) {
        viewModelScope.launch {
            tasbeehRepository.deleteTasbeeh(id)
            _event.send(TasbeehEvent.Deleted)
        }
    }


    private fun onTasbeehTextChanged(value: String) = _state.update { it.copy(tasbeehText = value) }
    private fun resetTasbeeh(id: Long) {
        viewModelScope.launch {
            when (val res = tasbeehRepository.updateTasbeehCount(id, 0)) {
                is AppResult.Done -> _event.send(TasbeehEvent.Reset)
                is AppResult.Error -> _event.send(TasbeehEvent.Error(res.error.asUiText()))
            }
        }
    }
}