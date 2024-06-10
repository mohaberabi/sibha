package com.mohaberabi.sibha.sibha_app

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope


data class SibhaState(
    val navController: NavHostController,
    val scope: CoroutineScope,
    val snackbarHostState: SnackbarHostState,
)


@Composable
fun rememberSibhaState(): SibhaState {
    val sibhaNavController = rememberNavController()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    return remember(
        sibhaNavController,
        scope,
        snackbarHostState,
    ) {
        SibhaState(
            navController = sibhaNavController,
            scope = scope,
            snackbarHostState = snackbarHostState,
        )
    }
}