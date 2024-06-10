package com.mohaberabi.sibha.sibha_app

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mohaberabi.sibha.core.presentation.compose.SibhaScaffold
import com.mohaberabi.sibha.core.presentation.navigation.SibhaNavHost
import com.mohaberabi.sibha.core.presentation.theme.SibhaTheme
import kotlinx.coroutines.launch


@Composable
fun SibhaComposeAppRoot(
    sibhaState: SibhaState
) {
    SibhaTheme {
        SibhaScaffold(
            snackBarHostState = sibhaState.snackbarHostState
        ) {
            SibhaNavHost(
                navController = sibhaState.navController,
                onShowSnackBar = {
                    sibhaState.scope.launch {
                        sibhaState.snackbarHostState.showSnackbar(it)
                    }
                }
            )

        }


    }

}