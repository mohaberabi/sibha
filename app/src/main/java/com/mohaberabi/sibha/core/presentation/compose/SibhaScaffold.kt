package com.mohaberabi.sibha.core.presentation.compose

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mohaberabi.sibha.core.util.const.CompUnit


@Composable
fun SibhaScaffold(
    snackBarHostState: SnackbarHostState? = null,
    modifier: Modifier = Modifier,
    topAppBar: @Composable () -> Unit = {},
    fab: @Composable () -> Unit = {},
    fabPosition: FabPosition = FabPosition.End,
    content: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(

        snackbarHost = {
            if (snackBarHostState != null) {
                SnackbarHost(hostState = snackBarHostState)
            }
        },
        floatingActionButton = fab,
        topBar = topAppBar,
        modifier = modifier,
    ) {
        content(it)
    }
}