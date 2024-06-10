package com.mohaberabi.sibha.core.image_storage

import com.mohaberabi.sibha.core.domain.image_storage.ImageStorage
import com.mohaberabi.sibha.core.util.AppResult
import com.mohaberabi.sibha.core.util.CommonError
import org.junit.jupiter.api.fail

class FakeImageStorage : ImageStorage {

    var imagePath: String = "image/image"
    var byteArray = byteArrayOf(0, 1, 2)
    var returnError: Boolean = false

    override suspend fun saveImageFromBytes(bytes: ByteArray): AppResult<String, CommonError> {
        return if (returnError) {
            AppResult.Error(CommonError.IOEXCEPTION)
        } else {
            AppResult.Done(imagePath)
        }
    }

    override suspend fun getImage(path: String): ByteArray {
        return byteArray
    }

    override suspend fun deleteImage(path: String) {
        imagePath = ""
    }

}