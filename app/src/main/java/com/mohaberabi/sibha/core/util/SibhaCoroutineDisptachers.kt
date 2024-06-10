package com.mohaberabi.sibha.core.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface SibhaCoroutineDispatchers {


    val ioDispatcher: CoroutineDispatcher
    val defaultDispatcher: CoroutineDispatcher
    val mainDispatcher: CoroutineDispatcher
    val unconfinedDispatcher: CoroutineDispatcher
}


class DefaultSibhaCoroutineDispatchers : SibhaCoroutineDispatchers {

    override val ioDispatcher: CoroutineDispatcher
        get() = Dispatchers.IO
    override val mainDispatcher: CoroutineDispatcher
        get() = Dispatchers.Main
    override val unconfinedDispatcher: CoroutineDispatcher
        get() = Dispatchers.Unconfined
    override val defaultDispatcher: CoroutineDispatcher
        get() = Dispatchers.Default
}