package com.mohaberabi.sibha

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.core.content.FileProvider
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import com.mohaberabi.sibha.core.domain.image_convertor.SibhaImageSource
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.io.InputStream

class TestUriRule(
    private val sibhaImageSource: SibhaImageSource
) : TestWatcher() {

    var fileName = "test.jpg"

    lateinit var uri: Uri
    lateinit var bitmap: Bitmap
    override fun starting(description: Description?) {

        super.starting(description)
        val context = ApplicationProvider.getApplicationContext<Context>()

        bitmap = when (sibhaImageSource) {
            is SibhaImageSource.ByteArrayResource -> BitmapFactory.decodeByteArray(
                sibhaImageSource.bytes,
                0,
                sibhaImageSource.bytes.size
            )

            is SibhaImageSource.RawResource -> BitmapFactory.decodeResource(
                context.resources,
                sibhaImageSource.id
            )
        }
        context.openFileOutput(
            fileName,
            Context.MODE_PRIVATE
        ).use { outputStream ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream)
        }

    }


    override fun finished(description: Description?) {
        super.finished(description)
        val context = ApplicationProvider.getApplicationContext<Context>()
        context.deleteFile(fileName)
    }

}