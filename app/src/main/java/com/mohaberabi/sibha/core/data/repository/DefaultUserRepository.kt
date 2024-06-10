package com.mohaberabi.sibha.core.data.repository

import com.mohaberabi.sibha.core.util.const.BackgroundShareType
import com.mohaberabi.sibha.core.util.const.SibhaBackgrounds
import com.mohaberabi.sibha.core.util.const.SibhaSound
import com.mohaberabi.sibha.core.domain.datasource.UserLocalDataSource
import com.mohaberabi.sibha.core.domain.model.UserPrefsModel
import com.mohaberabi.sibha.core.domain.image_storage.ImageStorage
import com.mohaberabi.sibha.core.domain.repository.UserRepository
import com.mohaberabi.sibha.core.util.AppResult
import com.mohaberabi.sibha.core.util.DataError
import com.mohaberabi.sibha.core.util.EmptyDataResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException

class DefaultUserRepository(
    private val userLocalDataSource: UserLocalDataSource,
    private val appScope: CoroutineScope,
    private val imageStorage: ImageStorage,
) : UserRepository {
    override fun getUserData(): Flow<UserPrefsModel> {
        return userLocalDataSource.getUserData().map { user ->
            val imageBytes = appScope.async {
                user.customBgPath?.let { path ->
                    imageStorage.getImage(path)
                }
            }.await()
            user.copy(customBgBytes = imageBytes)
        }
    }

    override suspend fun updateFontSize(size: Int): EmptyDataResult<DataError> {
        return try {
            userLocalDataSource.updateFontSize(size)
            AppResult.Done(Unit)
        } catch (e: IOException) {
            e.printStackTrace()
            AppResult.Error(DataError.Local.IOEXCEPTION)
        } catch (e: Exception) {
            e.printStackTrace()
            AppResult.Error(DataError.Local.UNKNOWN)
        }
    }

    override suspend fun updateBackground(
        newBg:
        SibhaBackgrounds
    ): EmptyDataResult<DataError> {
        return try {
            userLocalDataSource.updateBackground(newBg)
            val userData = userLocalDataSource.getUserData().first()
            userData.customBgPath?.let { path ->
                appScope.async {
                    imageStorage.deleteImage(path)
                }.await()
            }
            appScope.async {
                userLocalDataSource.updateDefaultBackground(null)
            }.await()
            AppResult.Done(Unit)
        } catch (e: IOException) {
            e.printStackTrace()
            AppResult.Error(DataError.Local.IOEXCEPTION)
        } catch (e: Exception) {
            e.printStackTrace()
            AppResult.Error(DataError.Local.UNKNOWN)
        }
    }

    override suspend fun updateSound(
        sound:
        SibhaSound
    ): EmptyDataResult<DataError> {
        return try {
            userLocalDataSource.updateSound(sound)
            AppResult.Done(Unit)
        } catch (e: IOException) {
            e.printStackTrace()
            AppResult.Error(DataError.Local.IOEXCEPTION)
        } catch (e: Exception) {
            e.printStackTrace()
            AppResult.Error(DataError.Local.UNKNOWN)
        }
    }

    override suspend fun updateNotifyCheckpoint(
        notifyCheckPoint:
        Set<Int>
    ): EmptyDataResult<DataError> {
        return try {
            userLocalDataSource.updateNotifyCheckpoint(notifyCheckPoint)
            AppResult.Done(Unit)
        } catch (e: IOException) {
            e.printStackTrace()
            AppResult.Error(DataError.Local.IOEXCEPTION)
        } catch (e: Exception) {
            e.printStackTrace()
            AppResult.Error(DataError.Local.UNKNOWN)
        }
    }

    override suspend fun updateBackgroundShareType(
        backgroundShareType:
        BackgroundShareType
    ): EmptyDataResult<DataError> {
        return try {
            userLocalDataSource.updateBackgroundShareType(backgroundShareType)
            AppResult.Done(Unit)
        } catch (e: IOException) {
            e.printStackTrace()
            AppResult.Error(DataError.Local.IOEXCEPTION)
        } catch (e: Exception) {
            e.printStackTrace()
            AppResult.Error(DataError.Local.UNKNOWN)
        }
    }

    override suspend fun updateDefaultBackground(
        background:
        String?
    ): EmptyDataResult<DataError> {
        return try {
            userLocalDataSource.updateDefaultBackground(background)
            AppResult.Done(Unit)
        } catch (e: IOException) {
            e.printStackTrace()
            AppResult.Error(DataError.Local.IOEXCEPTION)
        } catch (e: Exception) {
            e.printStackTrace()
            AppResult.Error(DataError.Local.UNKNOWN)
        }
    }


}