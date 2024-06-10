package com.mohaberabi.sibha.core.data.image_convertor

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import com.mohaberabi.sibha.core.domain.image_convertor.ImageConvertor
import com.mohaberabi.sibha.core.domain.image_convertor.SibhaImageSource
import com.mohaberabi.sibha.core.util.AppResult
import com.mohaberabi.sibha.core.util.CommonError
import com.mohaberabi.sibha.core.util.SibhaCoroutineDispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException


class AndroidImageConvertor(
    private val context: Context,
    private val coroutineDispatchers: SibhaCoroutineDispatchers,

    ) : ImageConvertor {
    override suspend fun convertImageToBitmap(
        source: SibhaImageSource,
        extraText: String
    ): AppResult<Bitmap, CommonError> {

        return withContext(coroutineDispatchers.defaultDispatcher) {
            try {
                val imageBitMap = when (source) {
                    is SibhaImageSource.ByteArrayResource -> BitmapFactory.decodeByteArray(
                        source.bytes,
                        0,
                        source.bytes.size
                    )

                    is SibhaImageSource.RawResource -> BitmapFactory.decodeResource(
                        context.resources,
                        source.id
                    )
                }

                AppResult.Done(imageBitMap)
            } catch (e: IOException) {
                e.printStackTrace()
                AppResult.Error(CommonError.IOEXCEPTION)
            }
        }

    }


}
//     val compressedStream = ByteArrayOutputStream()
//                imageBitMap.compress(
//                    Bitmap.CompressFormat.JPEG,
//                    25,
//                    compressedStream
//                )
//                val bitmap =
//                    Bitmap.createBitmap(
//                        imageBitMap.width,
//                        imageBitMap.height,
//                        Bitmap.Config.ARGB_8888
//                    )
//                val canvas = Canvas(bitmap)
//                canvas.drawBitmap(
//                    imageBitMap,
//                    0f,
//                    0f,
//                    null
//                )
//                val paint = Paint().apply {
//                    color = Color.WHITE
//                    textSize = 50f
//                    textAlign = Paint.Align.CENTER
//                }
//                val textBounds = Rect()
//                paint.getTextBounds(extraText, 0, extraText.length, textBounds)
//                val x = bitmap.width / 2f
//                val y = (bitmap.height + textBounds.height()) / 2f
//                canvas.drawText(extraText, x, y, paint)