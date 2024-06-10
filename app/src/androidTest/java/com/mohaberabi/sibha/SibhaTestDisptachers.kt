package com.mohaberabi.sibha

import com.mohaberabi.sibha.core.util.SibhaCoroutineDispatchers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher

class SibhaTestDisptachers(
    val testDispatcher: TestDispatcher = StandardTestDispatcher()

) : SibhaCoroutineDispatchers {

    override val ioDispatcher: CoroutineDispatcher
        get() = testDispatcher
    override val defaultDispatcher: CoroutineDispatcher
        get() = testDispatcher
    override val mainDispatcher: CoroutineDispatcher
        get() = testDispatcher
    override val unconfinedDispatcher: CoroutineDispatcher
        get() = testDispatcher
}