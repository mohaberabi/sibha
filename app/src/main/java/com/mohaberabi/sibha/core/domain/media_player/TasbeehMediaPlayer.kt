package com.mohaberabi.sibha.core.domain.media_player

import androidx.annotation.RawRes
import com.mohaberabi.sibha.core.util.CommonError
import com.mohaberabi.sibha.core.util.EmptyDataResult


interface TasbeehMediaPlayer {

    suspend fun playSound(source: SibhaSoundSource): EmptyDataResult<CommonError>
}


sealed interface SibhaSoundSource {
    data class Raw(@RawRes val id: Int) : SibhaSoundSource
}

