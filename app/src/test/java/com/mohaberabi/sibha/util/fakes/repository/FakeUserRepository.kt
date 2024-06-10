package com.mohaberabi.sibha.util.fakes.repository

import androidx.datastore.preferences.protobuf.Empty
import com.mohaberabi.sibha.core.domain.model.UserPrefsModel
import com.mohaberabi.sibha.core.domain.repository.UserRepository
import com.mohaberabi.sibha.core.util.AppResult
import com.mohaberabi.sibha.core.util.DataError
import com.mohaberabi.sibha.core.util.EmptyDataResult
import com.mohaberabi.sibha.core.util.const.BackgroundShareType
import com.mohaberabi.sibha.core.util.const.SibhaBackgrounds
import com.mohaberabi.sibha.core.util.const.SibhaSound
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf

class FakeUserRepository : UserRepository {
    var user = UserPrefsModel()
    var returnError: Boolean = false

    override fun getUserData(): Flow<UserPrefsModel> = MutableStateFlow(user)

    override suspend fun updateFontSize(
        size: Int,
    ): EmptyDataResult<DataError> = returnOrError {
        user = user.copy(fontSize = size)
    }

    override suspend fun updateBackground(
        newBg: SibhaBackgrounds,
    ): EmptyDataResult<DataError> =
        returnOrError {
            user = user.copy(background = newBg)
        }

    override suspend fun updateSound(
        sound: SibhaSound,
    ): EmptyDataResult<DataError> =
        returnOrError {
            user = user.copy(sound = sound)
        }


    override suspend fun updateNotifyCheckpoint(
        notifyCheckPoint: Set<Int>,
    ): EmptyDataResult<DataError> =
        returnOrError {
            user = user.copy(notifyCheckpoints = notifyCheckPoint)
        }

    override suspend fun updateBackgroundShareType(
        backgroundShareType:
        BackgroundShareType
    ): EmptyDataResult<DataError> =
        returnOrError {
        }

    override suspend fun updateDefaultBackground(
        background: String?,
    ): EmptyDataResult<DataError> = returnOrError {
        user = user.copy(customBgPath = background)
    }


    private fun returnOrError(
        before: () -> Unit
    ): EmptyDataResult<DataError> {
        return if (returnError) {
            AppResult.Error(DataError.Local.UNKNOWN)
        } else {
            before()
            AppResult.Done(Unit)
        }

    }
}