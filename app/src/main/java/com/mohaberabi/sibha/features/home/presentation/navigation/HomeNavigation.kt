package com.mohaberabi.sibha.features.home.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mohaberabi.sibha.features.home.presentation.screen.HomeScreenRoot
import com.mohaberabi.sibha.features.settings.presentation.navigation.navigateToSettings
import kotlinx.serialization.Serializable


@Serializable
object HomeRoute


fun NavGraphBuilder.homeScreen(
    navController: NavController,
    onShowSnackBar: (String) -> Unit,
) = composable<HomeRoute> {
    HomeScreenRoot(
        onGoToSettings = {
            navController.navigateToSettings()
        },
        onShowSnackBar = onShowSnackBar
    )
}