package com.mohaberabi.sibha.core.domain.datasource

import com.mohaberabi.sibha.core.util.const.BackgroundShareType
import com.mohaberabi.sibha.core.util.const.SibhaBackgrounds
import com.mohaberabi.sibha.core.util.const.SibhaSound
import com.mohaberabi.sibha.core.domain.model.UserPrefsModel
import com.mohaberabi.sibha.core.util.AppError
import com.mohaberabi.sibha.core.util.CommonError
import com.mohaberabi.sibha.core.util.DataError
import com.mohaberabi.sibha.core.util.EmptyDataResult
import kotlinx.coroutines.flow.Flow

interface UserLocalDataSource {
    fun getUserData(): Flow<UserPrefsModel>
    suspend fun updateFontSize(size: Int)
    suspend fun updateBackground(newBg: SibhaBackgrounds)
    suspend fun updateSound(sound: SibhaSound)
    suspend fun updateNotifyCheckpoint(notifyCheckPoint: Set<Int>)
    suspend fun updateBackgroundShareType(backgroundShareType: BackgroundShareType)
    suspend fun updateDefaultBackground(background: String?)
}