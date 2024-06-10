package com.mohaberabi.sibha.features.backgrounds.presentation.viewmodel

import com.google.common.truth.Truth.assertThat
import com.mohaberabi.sibha.util.fakes.repository.FakeUserRepository
import com.mohaberabi.sibha.core.image_storage.FakeImageStorage
import com.mohaberabi.sibha.core.util.AppResult
import com.mohaberabi.sibha.core.util.CommonError
import com.mohaberabi.sibha.core.util.DataError
import com.mohaberabi.sibha.core.util.const.SibhaBackgrounds
import com.mohaberabi.sibha.util.MainCoroutineDispatcherExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MainCoroutineDispatcherExtension::class)
class BackgroundsViewModelTest {


    /**
     * [advanceUntilIdle]
     * Advances the testScheduler to the point where there are no tasks remaining.
     */
    private lateinit var backgroundsViewModel: BackgroundsViewModel


    private lateinit var userRepository: FakeUserRepository
    private lateinit var imageStorage: FakeImageStorage


    @BeforeEach
    fun setup() {
        userRepository = FakeUserRepository()
        imageStorage = FakeImageStorage()
        backgroundsViewModel = BackgroundsViewModel(userRepository, imageStorage)
    }


    @Test
    fun ` when viewmodel onAction is onBackgroundClick should change user backGround when no error `() =
        runTest {

            backgroundsViewModel.onAction(BackgroundAction.OnBackGroundClick(SibhaBackgrounds.LANDSCAPE_5))
            val res = userRepository.updateBackground(SibhaBackgrounds.LANDSCAPE_5)

            advanceUntilIdle()
            assertThat(userRepository.user.background).isEqualTo(SibhaBackgrounds.LANDSCAPE_5)
            assertThat(res).isEqualTo(AppResult.Done(Unit))
            assertThat(backgroundsViewModel.event.first()).isEqualTo(BackgroundEvent.ChangedBg)
        }

    @Test
    fun ` when viewmodel onAction is onBackgroundClick should not change user backGround when no error  `() =
        runTest {


            userRepository.returnError = true
            val dataError = DataError.Local.UNKNOWN
            backgroundsViewModel.onAction(BackgroundAction.OnBackGroundClick(SibhaBackgrounds.LANDSCAPE_5))
            val res = userRepository.updateBackground(SibhaBackgrounds.LANDSCAPE_1)
            advanceUntilIdle()
            assertThat(userRepository.user.background).isEqualTo(SibhaBackgrounds.LANDSCAPE_1)
            assertThat(res).isEqualTo(AppResult.Error(dataError))
            assertThat(backgroundsViewModel.event.first()).isInstanceOf(BackgroundEvent.Error::class.java)
        }


    @Test
    fun `when viewmodel onaction is OnImagePicked should save the resultant byteArray to user  when no errors `() =
        runTest {
            val bytes = byteArrayOf(0, 1, 2)
            backgroundsViewModel.onAction(BackgroundAction.OnImagePicked(bytes))
            val res = imageStorage.saveImageFromBytes(bytes)
            advanceUntilIdle()
            assertThat(res).isEqualTo(AppResult.Done(imageStorage.imagePath))
            assertThat(userRepository.user.customBgPath).isEqualTo(imageStorage.imagePath)
            assertThat(backgroundsViewModel.event.first()).isEqualTo(BackgroundEvent.ChangedBg)
        }

    @Test
    fun `when viewmodel onaction is OnImagePicked should not  save the resultant byteArray to user`() =
        runTest {
            imageStorage.returnError = true
            val bytes = byteArrayOf(0, 1, 2)
            backgroundsViewModel.onAction(BackgroundAction.OnImagePicked(bytes))
            val res = imageStorage.saveImageFromBytes(bytes)
            advanceUntilIdle()
            val error = CommonError.IOEXCEPTION
            assertThat(res).isEqualTo(AppResult.Error(error))
            assertThat(userRepository.user.customBgPath).isEqualTo(null)
            assertThat(backgroundsViewModel.event.first()).isInstanceOf(BackgroundEvent.Error::class.java)
        }

    @Test
    fun `when viewmodel onaction is OnImagePicked should not  save the resultant byteArray to user when the bytesArray is null`() =
        runTest {
            val bytes = null
            backgroundsViewModel.onAction(BackgroundAction.OnImagePicked(bytes))
            advanceUntilIdle()
            assertThat(userRepository.user.customBgPath).isEqualTo(null)
        }


}