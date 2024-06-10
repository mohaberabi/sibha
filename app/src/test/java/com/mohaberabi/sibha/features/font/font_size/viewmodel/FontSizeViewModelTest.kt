package com.mohaberabi.sibha.features.font.font_size.viewmodel

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.mohaberabi.sibha.util.MainCoroutineDispatcherExtension
import com.mohaberabi.sibha.util.fakes.repository.FakeUserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith


@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MainCoroutineDispatcherExtension::class)
class FontSizeViewModelTest {

    private lateinit var userRepository: FakeUserRepository

    private lateinit var fontSizeViewModel: FontSizeViewModel


    @BeforeEach
    fun setup() {
        userRepository = FakeUserRepository()
        fontSizeViewModel = FontSizeViewModel(userRepository)
    }


    @Test
    fun `when action is OnSaveFontSize updates font size in repo and returns done`() = runTest {
        fontSizeViewModel.state.test {
            val emit1 = awaitItem()
            assertThat(emit1.loading).isEqualTo(false)
            fontSizeViewModel.onAction(FontSizeAction.OnSaveFontSize(20))
            val emit2 = awaitItem()
            assertThat(emit2.loading).isEqualTo(true)
        }
        advanceUntilIdle()
        assertThat(userRepository.user.fontSize).isEqualTo(20)
        assertThat(fontSizeViewModel.event.first()).isInstanceOf(FontSizeEvent.Saved::class.java)
    }

    @Test
    fun `when action is OnSaveFontSize updates font size in repo and returns error`() = runTest {
        userRepository.returnError = true
        fontSizeViewModel.state.test {
            val emit1 = awaitItem()
            assertThat(emit1.loading).isEqualTo(false)
            fontSizeViewModel.onAction(FontSizeAction.OnSaveFontSize(20))
            val emit2 = awaitItem()
            assertThat(emit2.loading).isEqualTo(true)
        }
        advanceUntilIdle()
        assertThat(userRepository.user.fontSize).isEqualTo(16)
        assertThat(fontSizeViewModel.event.first()).isInstanceOf(FontSizeEvent.Error::class.java)

    }
}