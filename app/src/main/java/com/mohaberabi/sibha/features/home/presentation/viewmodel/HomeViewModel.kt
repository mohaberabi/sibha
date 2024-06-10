package com.mohaberabi.sibha.features.home.presentation.viewmodel

import android.os.SystemClock
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohaberabi.sibha.R
import com.mohaberabi.sibha.core.domain.image_convertor.ImageConvertor
import com.mohaberabi.sibha.core.domain.image_convertor.SibhaImageSource
import com.mohaberabi.sibha.core.domain.vibration.SibhaVibrator
import com.mohaberabi.sibha.core.domain.media_player.TasbeehMediaPlayer
import com.mohaberabi.sibha.core.domain.repository.TasbeehRepository
import com.mohaberabi.sibha.core.domain.media_player.SibhaSoundSource
import com.mohaberabi.sibha.core.domain.notifications.SibhaNotificationManager
import com.mohaberabi.sibha.core.domain.reminder.SibhaReminder
import com.mohaberabi.sibha.core.domain.reminder.SibhaReminderData
import com.mohaberabi.sibha.core.domain.repository.UserRepository
import com.mohaberabi.sibha.core.util.AppResult
import com.mohaberabi.sibha.core.util.UiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val tasbeehMediaPlayer: TasbeehMediaPlayer,
    private val tasbeehRepository: TasbeehRepository,
    private val userRepository: UserRepository,
    private val sibhaVibrator: SibhaVibrator,
    private val imageConvertor: ImageConvertor,
    private val sibhaNotificationManager: SibhaNotificationManager,
) : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()


    val _event = Channel<HomeEvent>()

    val event = _event.receiveAsFlow()


    init {

        tasbeehRepository.getAllTasbeeh()
            .combine(userRepository.getUserData()) { tasbeehs, user ->
                _state.update {
                    it.copy(
                        user = user,
                        tasbeeh = if (_state.value.tasbeeh == null)
                            tasbeehs.firstOrNull() else _state.value.tasbeeh,
                        tasbeehs = tasbeehs
                    )
                }
            }.launchIn(viewModelScope)

        viewModelScope.launch {
            val isEmpty = tasbeehRepository.getTasbeehsCount() == 0
            if (isEmpty) {
                tasbeehRepository.addDefaultTasbeehs()
            }
        }
    }

    fun onAction(action: HomeAction) {
        when (action) {
            HomeAction.OnIncrementTasbeeh -> incrementTasbeeh()
            HomeAction.OnToggleSound -> toggleSound()
            HomeAction.OnResetTasbeeh -> resetCounter()
            HomeAction.OnToggleVibration -> toggleVibration()
            HomeAction.OnShareClick -> shareImage()
            HomeAction.DismissRationalDialog -> _state.update {
                it.copy(
                    showNotificationRational = false,
                    notificaitonGranted = false
                )
            }

            is HomeAction.OnTasbeehClick -> _state.update { it.copy(tasbeeh = action.tasbeeh) }
            is HomeAction.SubmitNotificaitonPermission -> onNotificationSubmit(
                action.isGranted,
                action.showRational
            )

            else -> Unit
        }
    }

    private fun onNotificationSubmit(
        isGranted: Boolean,
        showRational: Boolean
    ) {
        _state.update {
            it.copy(
                showNotificationRational = showRational,
                notificaitonGranted = isGranted
            )
        }
    }

    private fun incrementTasbeeh() {
        viewModelScope.launch {
            val tasbeeh = state.value.tasbeeh!!
            val count = tasbeeh.count + 1
            tasbeehRepository.updateTasbeehCount(
                tasbeehId = tasbeeh.id!!,
                count = count
            )
            _state.update {
                it.copy(
                    tasbeeh = tasbeeh.copy(count = count)
                )
            }
            vibrateIfNeeded()
            playSoundIfNeeded()
            showCheckpointNotification(count)
        }
    }


    private fun vibrateIfNeeded() {
        if (_state.value.vibrate) {
            sibhaVibrator.vibrate(500L)
        }
    }

    private fun playSoundIfNeeded() {
        if (_state.value.playSound) {
            viewModelScope.launch {
                tasbeehMediaPlayer.playSound(SibhaSoundSource.Raw(_state.value.user.sound.id))
            }
        }
    }

    private fun resetCounter() =
        _state.update { it.copy(tasbeeh = _state.value.tasbeeh!!.copy(count = 0)) }

    private fun toggleVibration() = _state.update { it.copy(vibrate = !it.vibrate) }

    private fun toggleSound() = _state.update { it.copy(playSound = !it.playSound) }


    private fun shareImage() {
        viewModelScope.launch {
            val user = state.value.user
            val source = if (user.customBgBytes != null) {
                SibhaImageSource.ByteArrayResource(user.customBgBytes)
            } else SibhaImageSource.RawResource(user.background.id)
            val bitmapRes =
                imageConvertor.convertImageToBitmap(
                    source,
                    _state.value.tasbeeh?.tasbeeh ?: ""
                )
            when (bitmapRes) {
                is AppResult.Done -> _event.send(HomeEvent.ReadyToShareImage(bitmapRes.data))
                is AppResult.Error -> _event.send(HomeEvent.Error(UiText.DynamicString(bitmapRes.error.toString())))
            }
        }
    }


    private fun showCheckpointNotification(count: Int) {
        if (_state.value.user.notifyCheckpoints.contains(count)) {
            sibhaNotificationManager.show(
                UiText.StringResource(R.string.tasbeeh_reminder),
                UiText.StringResource(R.string.you_have_reached, arrayOf(count.toString())),
            )
        }
    }
}
