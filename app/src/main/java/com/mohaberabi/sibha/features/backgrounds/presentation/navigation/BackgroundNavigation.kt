package com.mohaberabi.sibha.features.backgrounds.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mohaberabi.sibha.features.backgrounds.presentation.screen.BackGroundsScreenRoot
import com.mohaberabi.sibha.features.home.presentation.navigation.HomeRoute
import kotlinx.serialization.Serializable


@Serializable
object BackgroundRoute

fun NavGraphBuilder.backgroundsScreen(
    navController: NavController,
) = composable<BackgroundRoute> {
    BackGroundsScreenRoot(
        onGoBack = { navController.popBackStack() },
        onGoHome = {
            navController.apply {
                popBackStack()
                popBackStack()
            }
        },
    ) {

    }
}

fun NavController.navigateToBackgrounds() = navigate(BackgroundRoute)