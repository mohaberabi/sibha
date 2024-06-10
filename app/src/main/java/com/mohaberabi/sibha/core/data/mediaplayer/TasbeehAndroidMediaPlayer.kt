package com.mohaberabi.sibha.core.data.mediaplayer

import android.content.Context
import android.media.MediaPlayer
import com.mohaberabi.sibha.core.domain.media_player.TasbeehMediaPlayer
import com.mohaberabi.sibha.core.domain.media_player.SibhaSoundSource
import com.mohaberabi.sibha.core.util.AppResult
import com.mohaberabi.sibha.core.util.CommonError
import com.mohaberabi.sibha.core.util.EmptyDataResult
import com.mohaberabi.sibha.core.util.SibhaCoroutineDispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class TasbeehAndroidMediaPlayer(
    private val context: Context,
    private val coroutineDisptachers: SibhaCoroutineDispatchers,
) : TasbeehMediaPlayer {
    override suspend fun playSound(source: SibhaSoundSource): EmptyDataResult<CommonError> {

        return withContext(coroutineDisptachers.ioDispatcher) {
            try {
                when (source) {
                    is SibhaSoundSource.Raw -> {
                        val mediaPlayer = MediaPlayer.create(context, source.id)
                        mediaPlayer.apply {
                            start()
                            setOnCompletionListener {
                                release()
                            }
                        }
                    }
                }
                AppResult.Done(Unit)
            } catch (e: IOException) {
                e.printStackTrace()
                AppResult.Error(CommonError.IOEXCEPTION)
            } catch (e: Exception) {
                e.printStackTrace()
                AppResult.Error(CommonError.UNKNOWN)
            }
        }

    }
}