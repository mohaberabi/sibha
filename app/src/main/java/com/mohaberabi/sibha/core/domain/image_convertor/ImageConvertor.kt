package com.mohaberabi.sibha.core.domain.image_convertor

import android.graphics.Bitmap
import androidx.annotation.DrawableRes
import com.mohaberabi.sibha.core.util.AppResult
import com.mohaberabi.sibha.core.util.CommonError

interface ImageConvertor {


    suspend fun convertImageToBitmap(
        source: SibhaImageSource, extraText: String,
    ): AppResult<Bitmap, CommonError>


}

sealed interface SibhaImageSource {

    data class RawResource(@DrawableRes val id: Int) : SibhaImageSource

    data class ByteArrayResource(val bytes: ByteArray) : SibhaImageSource

}