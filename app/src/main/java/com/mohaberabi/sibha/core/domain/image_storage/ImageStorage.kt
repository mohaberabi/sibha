package com.mohaberabi.sibha.core.domain.image_storage

import android.graphics.Bitmap
import com.mohaberabi.sibha.core.util.AppResult
import com.mohaberabi.sibha.core.util.CommonError

interface ImageStorage {
    suspend fun saveImageFromBytes(bytes: ByteArray): AppResult<String, CommonError>
    suspend fun saveImageFromBitMap(bitmap: Bitmap): AppResult<String, CommonError>

    suspend fun getImage(path: String): ByteArray?
    suspend fun deleteImage(path: String)
}