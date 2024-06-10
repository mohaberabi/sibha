package com.mohaberabi.sibha.core.data.repository

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.mohaberabi.sibha.core.image_storage.FakeImageStorage
import com.mohaberabi.sibha.util.fakes.source.FakeUserDataSource
import com.mohaberabi.sibha.core.util.AppResult
import com.mohaberabi.sibha.core.util.DataError
import com.mohaberabi.sibha.core.util.const.SibhaBackgrounds
import com.mohaberabi.sibha.core.util.const.SibhaSound
import com.mohaberabi.sibha.util.MainCoroutineDispatcherExtension
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import okio.IOException
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MainCoroutineDispatcherExtension::class)
class DefaultUserRepositoryTest {


    private lateinit var userRepository: DefaultUserRepository

    private lateinit var userLocalDataSource: FakeUserDataSource
    private lateinit var imageStorage: FakeImageStorage


    private lateinit var scope: CoroutineScope

    @BeforeEach

    fun setup() {
        scope = TestScope()
        userLocalDataSource = FakeUserDataSource()
        imageStorage = FakeImageStorage()

        userRepository = DefaultUserRepository(userLocalDataSource, scope, imageStorage)
    }


    @Test
    fun `when updateFontSize called it should return done and updates user font in local source`() =
        runTest {
            val res = userRepository.updateFontSize(12)
            assertThat(userLocalDataSource.user.fontSize).isEqualTo(12)
            assertThat(res).isEqualTo(AppResult.Done(Unit))
        }

    @Test
    fun `when updateFontSize called it should return error  never updates user font in local source when io excetption thrown`() =
        runTest {
            userLocalDataSource.throwException = true
            userLocalDataSource.exceptionToThrow = IOException()
            val res = userRepository.updateFontSize(12)
            assertThat(userLocalDataSource.user.fontSize).isEqualTo(16)
            assertThat(res).isEqualTo(AppResult.Error(DataError.Local.IOEXCEPTION))
        }

    @Test
    fun `when updateFontSize called it should return error  never updates user font in local source when  excetption thrown`() =
        runTest {
            userLocalDataSource.throwException = true
            userLocalDataSource.exceptionToThrow = IllegalArgumentException()
            val res = userRepository.updateFontSize(12)
            assertThat(userLocalDataSource.user.fontSize).isEqualTo(16)
            assertThat(res).isEqualTo(AppResult.Error(DataError.Local.UNKNOWN))
        }


    @Test
    fun `when user updateBackground it should return done with udpating the user bg `() =
        runTest {
            val res = userRepository.updateBackground(SibhaBackgrounds.LANDSCAPE_5)
            assertThat(res).isEqualTo(AppResult.Done(Unit))
            assertThat(userLocalDataSource.user.background).isEqualTo(SibhaBackgrounds.LANDSCAPE_5)
            assertThat(userLocalDataSource.user.customBgPath).isEqualTo(null)
            assertThat(imageStorage.imagePath).isNotEmpty()
        }


    @Test
    fun `when updateSound called it should return done and updates user sound in local source`() =
        runTest {
            val res = userRepository.updateSound(SibhaSound.SOUND_1)
            assertThat(userLocalDataSource.user.sound).isEqualTo(SibhaSound.SOUND_1)
            assertThat(res).isEqualTo(AppResult.Done(Unit))
        }

    @Test
    fun `when updateSound called it should return error  never updates user sound in local source when io excetption thrown`() =
        runTest {
            userLocalDataSource.throwException = true
            userLocalDataSource.exceptionToThrow = IOException()
            val res = userRepository.updateSound(SibhaSound.SOUND_1)
            assertThat(userLocalDataSource.user.sound).isEqualTo(SibhaSound.SOUND_1)
            assertThat(res).isEqualTo(AppResult.Error(DataError.Local.IOEXCEPTION))
        }

    @Test
    fun `when updateSound called it should return error  never updates user sound  in local source when  excetption thrown`() =
        runTest {
            userLocalDataSource.throwException = true
            userLocalDataSource.exceptionToThrow = IllegalArgumentException()
            val res = userRepository.updateSound(SibhaSound.SOUND_1)
            assertThat(userLocalDataSource.user.sound).isEqualTo(SibhaSound.SOUND_1)
            assertThat(res).isEqualTo(AppResult.Error(DataError.Local.UNKNOWN))
        }


    @Test
    fun `when updateNotifyCheckpoint called it should return done and updates user sound in local source`() =
        runTest {
            val res = userRepository.updateNotifyCheckpoint(setOf(1))
            assertThat(userLocalDataSource.user.notifyCheckpoints).isEqualTo(setOf(1))
            assertThat(res).isEqualTo(AppResult.Done(Unit))
        }


    @Test
    fun `when updateNotifyCheckpoint called it should return error  never updates user notifct   in local source when  excetption thrown`() =
        runTest {
            userLocalDataSource.throwException = true
            userLocalDataSource.exceptionToThrow = Exception()
            val res = userRepository.updateNotifyCheckpoint(setOf<Int>(1, 2))
            assertThat(userLocalDataSource.user.notifyCheckpoints).isEqualTo(emptySet<Int>())
            assertThat(res).isEqualTo(AppResult.Error(DataError.Local.UNKNOWN))
        }


    @Test
    fun ` when user repositroy get userdata subscribed to should return correct user `() = runTest {

        userRepository.getUserData().test {
            val emit1 = awaitItem()
            assertThat(emit1).isEqualTo(userLocalDataSource.user)
        }
    }

    @AfterEach
    fun tearDown() {
        scope.cancel()
    }
}