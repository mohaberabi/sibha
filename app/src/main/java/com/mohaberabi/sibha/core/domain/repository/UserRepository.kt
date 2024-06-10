package com.mohaberabi.sibha.core.domain.repository

import com.mohaberabi.sibha.core.util.const.BackgroundShareType
import com.mohaberabi.sibha.core.util.const.SibhaBackgrounds
import com.mohaberabi.sibha.core.util.const.SibhaSound
import com.mohaberabi.sibha.core.domain.model.UserPrefsModel
import com.mohaberabi.sibha.core.util.DataError
import com.mohaberabi.sibha.core.util.EmptyDataResult
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getUserData(): Flow<UserPrefsModel>
    suspend fun updateFontSize(size: Int): EmptyDataResult<DataError>
    suspend fun updateBackground(newBg: SibhaBackgrounds): EmptyDataResult<DataError>
    suspend fun updateSound(sound: SibhaSound): EmptyDataResult<DataError>
    suspend fun updateNotifyCheckpoint(notifyCheckPoint: Set<Int>): EmptyDataResult<DataError>
    suspend fun updateBackgroundShareType(backgroundShareType: BackgroundShareType): EmptyDataResult<DataError>
    suspend fun updateDefaultBackground(background: String?): EmptyDataResult<DataError>
}