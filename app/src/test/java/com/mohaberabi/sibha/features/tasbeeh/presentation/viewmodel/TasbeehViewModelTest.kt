package com.mohaberabi.sibha.features.tasbeeh.presentation.viewmodel

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.mohaberabi.sibha.util.MainCoroutineDispatcherExtension
import com.mohaberabi.sibha.util.fakes.repository.FakeTasbeehRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MainCoroutineDispatcherExtension::class)
class TasbeehViewModelTest {

    private lateinit var tasbeehViewModel: TasbeehViewModel

    private lateinit var tasbeehRepository: FakeTasbeehRepository


    @BeforeEach
    fun setup() {
        tasbeehRepository = FakeTasbeehRepository()
        tasbeehViewModel = TasbeehViewModel(tasbeehRepository)
    }


    @Test
    fun `when onAction() is OnSaveAddedTasbeh addTasbeehCalled from repo adds tasbeeh returns done `() =
        runTest {
            tasbeehViewModel.state.test {
                awaitItem()
                tasbeehViewModel.onAction(TasbeehAction.OnSaveAddedTasbeh)
                val emit2 = awaitItem()
                assertThat(emit2.loading).isEqualTo(true)
                val emit3 = awaitItem()
                assertThat(emit3.loading).isEqualTo(false)
            }
            advanceUntilIdle()
            assertThat(tasbeehRepository.called).isEqualTo(1)
            assertThat(tasbeehRepository.tasbeehsMap.size).isEqualTo(4)
            assertThat(tasbeehViewModel.event.first()).isInstanceOf(TasbeehEvent.Added::class.java)
        }

    @Test
    fun `when onAction() is OnSaveAddedTasbeh  never addTasbeehCalled from repo adds tasbeeh returns error `() =
        runTest {
            tasbeehRepository.returnError = true
            tasbeehViewModel.state.test {
                awaitItem()
                tasbeehViewModel.onAction(TasbeehAction.OnSaveAddedTasbeh)
                val emit2 = awaitItem()
                assertThat(emit2.loading).isEqualTo(true)
                val emit3 = awaitItem()
                assertThat(emit3.loading).isEqualTo(false)
            }
            advanceUntilIdle()
            assertThat(tasbeehRepository.called).isEqualTo(1)
            assertThat(tasbeehRepository.tasbeehsMap.size).isEqualTo(3)
            assertThat(tasbeehViewModel.event.first()).isInstanceOf(TasbeehEvent.Error::class.java)
        }


    @Test
    fun `when onAction() is OnDeleteTasbeeh() deletes from repo and returns done`() = runTest {

        tasbeehViewModel.onAction(TasbeehAction.OnDeleteTasbeeh(0L))
        advanceUntilIdle()
        assertThat(tasbeehRepository.called).isEqualTo(1)
        assertThat(tasbeehRepository.tasbeehsMap.size).isEqualTo(2)
        assertThat(tasbeehRepository.tasbeehsMap.containsKey(0L)).isEqualTo(false)
        assertThat(tasbeehViewModel.event.first()).isInstanceOf(TasbeehEvent.Deleted::class.java)
    }

    @Test
    fun `when onAction() is OnTasbeehTextChanged updates the text in the state`() {

        tasbeehViewModel.onAction(TasbeehAction.OnTasbeehTextChanged("test"))
        assertThat(tasbeehViewModel.state.value.tasbeehText).isEqualTo("test")
    }

    @Test
    fun `when onAction() is OnResetTeasbeeh it resets tasbeeh count to 0 `() = runTest {
        tasbeehViewModel.onAction(TasbeehAction.OnResetTasbeeh(0L))
        advanceUntilIdle()
        assertThat(tasbeehRepository.called).isEqualTo(1)
        assertThat(tasbeehRepository.tasbeehsMap[0L]!!.count).isEqualTo(0)
        assertThat(tasbeehViewModel.event.first()).isInstanceOf(TasbeehEvent.Reset::class.java)
    }

    @Test
    fun `when onAction() is OnResetTeasbeeh it never  resets tasbeeh count to 0 `() = runTest {
        tasbeehRepository.returnError = true
        tasbeehViewModel.onAction(TasbeehAction.OnResetTasbeeh(0L))
        advanceUntilIdle()
        assertThat(tasbeehRepository.called).isEqualTo(1)
        assertThat(tasbeehRepository.tasbeehsMap[0L]!!.count).isEqualTo(100)
        assertThat(tasbeehViewModel.event.first()).isInstanceOf(TasbeehEvent.Error::class.java)
    }

    @Test
    fun ` viewmodel state emitions `() = runTest {
        tasbeehViewModel.state.test {
            val emit = awaitItem()
            assertThat(emit.tasbeehs.size).isEqualTo(3)
        }
    }
}