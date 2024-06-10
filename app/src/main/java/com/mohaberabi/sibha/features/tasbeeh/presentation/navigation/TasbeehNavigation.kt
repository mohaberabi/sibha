package com.mohaberabi.sibha.features.tasbeeh.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mohaberabi.sibha.features.tasbeeh.presentation.screen.TasbeehScreenRoot
import kotlinx.serialization.Serializable

@Serializable
object TasbeehRoute


fun NavGraphBuilder.tasbeehScreen(
    navController: NavController,
    onShowSnackBar: (String) -> Unit,
) = composable<TasbeehRoute> {
    TasbeehScreenRoot(
        onShowSnackBar = onShowSnackBar,
        onGoBack = { navController.popBackStack() },
    )
}

fun NavController.navigateToTasbeehScreen() = navigate(TasbeehRoute)