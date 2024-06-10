package com.mohaberabi.sibha.util.fakes.source

import com.mohaberabi.sibha.core.domain.datasource.UserLocalDataSource
import com.mohaberabi.sibha.core.domain.model.UserPrefsModel
import com.mohaberabi.sibha.core.util.const.BackgroundShareType
import com.mohaberabi.sibha.core.util.const.SibhaBackgrounds
import com.mohaberabi.sibha.core.util.const.SibhaSound
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow


class FakeUserDataSource : UserLocalDataSource {

    var user = UserPrefsModel()
    var exceptionToThrow: Exception = Exception()
    var throwException: Boolean = false
    private val flow = MutableStateFlow(user)

    override fun getUserData(): Flow<UserPrefsModel> = flow

    override suspend fun updateFontSize(size: Int) = doOrThrow {
        user = user.copy(fontSize = size)
    }

    override suspend fun updateBackground(newBg: SibhaBackgrounds) = doOrThrow {
        user = user.copy(background = newBg)
    }

    override suspend fun updateSound(sound: SibhaSound) = doOrThrow {
        user = user.copy(sound = sound)
    }

    override suspend fun updateNotifyCheckpoint(
        notifyCheckPoint: Set<Int>,
    ) = doOrThrow {
        user = user.copy(notifyCheckpoints = notifyCheckPoint)
    }

    override suspend fun updateBackgroundShareType(
        backgroundShareType: BackgroundShareType,
    ) =
        doOrThrow {
            user = user.copy()
        }

    override suspend fun updateDefaultBackground(
        background: String?,
    ) = doOrThrow {
        user = user.copy(customBgPath = background)
    }

    private fun doOrThrow(
        action: () -> Unit,
    ) {
        if (throwException) {
            throw exceptionToThrow
        } else {
            action()
        }
    }
}