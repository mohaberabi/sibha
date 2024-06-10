package com.mohaberabi.sibha.core.data.image_storage

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.mohaberabi.sibha.R
import com.mohaberabi.sibha.SibhaTestDisptachers
import com.mohaberabi.sibha.TestUriRule
import com.mohaberabi.sibha.core.domain.image_convertor.SibhaImageSource
import com.mohaberabi.sibha.core.util.AppResult
import com.mohaberabi.sibha.core.util.SibhaCoroutineDispatchers
import com.mohaberabi.sibha.util.AndroidTestKoinRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.component.inject
import org.koin.dsl.module
import org.koin.test.KoinTest

@RunWith(AndroidJUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)

class AndroidImageStorageTest : KoinTest {

    val module = module {


        single<SibhaCoroutineDispatchers> {
            SibhaTestDisptachers()
        }
        single {
            AndroidImageStorage(get(), get())
        }
    }

    @get:Rule
    val koinRule = AndroidTestKoinRule(
        modules = listOf(module)
    )


    @get:Rule
    val uriTestRule = TestUriRule(
        sibhaImageSource = SibhaImageSource.RawResource(R.drawable.land1),
    )

    private val dispatchers: SibhaCoroutineDispatchers by inject()


    @Before

    fun setup() {

        Dispatchers.setMain(dispatchers.mainDispatcher)
    }


    private val androidImageStorage: AndroidImageStorage by inject()

    @Test
    fun saveImageFromBitmapReturnsCorrectPath() = runTest {
        val result = androidImageStorage.saveImageFromBitMap(uriTestRule.bitmap)
        assert(result is AppResult.Done)
        val filePath = (result as AppResult.Done).data
        assert(filePath.isNotEmpty())
        Log.i("path", filePath)
//        val retrievedBitmap = BitmapFactory.decodeFile(filePath)
//        assert(retrievedBitmap != null)
    }


//    @Test
//    fun getImageShouldReturnSameBytes() = runTest {
//        val result = androidImageStorage.saveImageFromBitMap(uriTestRule.bitmap)
//        assert(result is AppResult.Done)
//
//        val res = androidImageStorage.getImage(uriTestRule.file.absoluteFile.toString())
//        assertThat(res.size).isEqualTo(uriTestRule.readTestImageBytes().size)
//    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}