package com.mohaberabi.sibha.features.backgrounds.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohaberabi.sibha.core.util.const.SibhaBackgrounds
import com.mohaberabi.sibha.core.domain.image_storage.ImageStorage
import com.mohaberabi.sibha.core.domain.repository.UserRepository
import com.mohaberabi.sibha.core.util.AppResult
import com.mohaberabi.sibha.core.util.UiText
import com.mohaberabi.sibha.core.util.asUiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class BackgroundsViewModel(
    private val userRepository: UserRepository,
    private val imageStorage: ImageStorage,
) : ViewModel() {


    val _event = Channel<BackgroundEvent>()
    val event = _event.receiveAsFlow()


    fun onAction(action: BackgroundAction) {
        when (action) {
            BackgroundAction.OnBackClick -> {}
            is BackgroundAction.OnBackGroundClick -> changeSibhaBackground(action.bg)
            is BackgroundAction.OnImagePicked -> pickupCustomImage(action.bytes)
        }
    }

    private fun pickupCustomImage(byteArray: ByteArray?) {
        if (byteArray != null) {
            viewModelScope.launch {
                when (val res = imageStorage.saveImageFromBytes(byteArray)) {
                    is AppResult.Done -> {
                        userRepository.updateDefaultBackground(res.data)
                        _event.send(BackgroundEvent.ChangedBg)
                    }

                    is AppResult.Error -> _event.send(BackgroundEvent.Error(UiText.DynamicString(res.error.toString())))
                }
            }
        }

    }

    private fun changeSibhaBackground(bg: SibhaBackgrounds) {

        viewModelScope.launch {
            when (val res = userRepository.updateBackground(bg)) {
                is AppResult.Done -> _event.send(BackgroundEvent.ChangedBg)
                is AppResult.Error -> _event.send(BackgroundEvent.Error(res.error.asUiText()))
            }
        }
    }

}