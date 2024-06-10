package com.mohaberabi.sibha.features.font.font_size.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohaberabi.sibha.core.domain.repository.UserRepository
import com.mohaberabi.sibha.core.util.AppResult
import com.mohaberabi.sibha.core.util.asUiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class FontSizeViewModel(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _event = Channel<FontSizeEvent>()

    val event = _event.receiveAsFlow()
    val _state = MutableStateFlow(FontSizeState())


    val state = combine(
        _state,
        userRepository.getUserData()
    ) { state, user ->
        state.copy(fontSize = user.fontSize)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        FontSizeState()
    )


    fun onAction(action: FontSizeAction) {
        when (action) {
            FontSizeAction.OnGoBack -> Unit
            is FontSizeAction.OnSaveFontSize -> saveNewFontSize(action.size)
        }
    }

    private fun saveNewFontSize(size: Int) {

        _state.update { it.copy(loading = true) }
        viewModelScope.launch {
            when (val result = userRepository.updateFontSize(size)) {
                is AppResult.Done -> _event.send(FontSizeEvent.Saved)
                is AppResult.Error -> _event.send(FontSizeEvent.Error(result.error.asUiText()))
            }
            _state.update { it.copy(loading = false) }

        }
    }
}