package com.mohaberabi.sibha.core.data.image_storage

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.core.content.FileProvider
import com.mohaberabi.sibha.core.domain.image_storage.ImageStorage
import com.mohaberabi.sibha.core.util.AppResult
import com.mohaberabi.sibha.core.util.CommonError
import com.mohaberabi.sibha.core.util.SibhaCoroutineDispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException
import java.util.UUID


class AndroidImageStorage(
    private val context: Context,
    private val coroutineDispatchers: SibhaCoroutineDispatchers
) : ImageStorage {

    companion object {
        const val DEFAULT_IMG_EXT = "sharred_tasbeeh_img.jpg"
    }
//                    outputStream.write(bytes)


    override suspend fun saveImageFromBytes(bytes: ByteArray): AppResult<String, CommonError> {
        return withContext(coroutineDispatchers.ioDispatcher) {
            try {
                val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                val fileName = DEFAULT_IMG_EXT
                context.openFileOutput(fileName, Context.MODE_PRIVATE).use { outputStream ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream)
                }
                AppResult.Done(fileName)
            } catch (e: IOException) {
                e.printStackTrace()
                AppResult.Error(CommonError.UNKNOWN)
            } catch (e: Exception) {
                e.printStackTrace()
                AppResult.Error(CommonError.UNKNOWN)
            }
        }
    }

    override suspend fun saveImageFromBitMap(bitmap: Bitmap): AppResult<String, CommonError> {

        return withContext(coroutineDispatchers.ioDispatcher) {
            try {
                val fileName = DEFAULT_IMG_EXT
                context.openFileOutput(fileName, Context.MODE_PRIVATE).use { outputStream ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream)
                }
                AppResult.Done(fileName)
            } catch (e: IOException) {
                e.printStackTrace()
                AppResult.Error(CommonError.UNKNOWN)
            } catch (e: Exception) {
                e.printStackTrace()
                AppResult.Error(CommonError.UNKNOWN)
            }
        }
    }


    override suspend fun getImage(path: String): ByteArray {
        return withContext(coroutineDispatchers.ioDispatcher) {
            context.openFileInput(path).use { inputStream ->
                inputStream.readBytes()
            }
        }
    }

    override suspend fun deleteImage(path: String) {
        return withContext(coroutineDispatchers.ioDispatcher) {
            context.deleteFile(path)
        }
    }
}