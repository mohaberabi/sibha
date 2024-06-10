package com.mohaberabi.sibha.features.notify_count.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mohaberabi.sibha.features.notify_count.presentation.screen.NotifyCountScreenRoot
import kotlinx.serialization.Serializable

@Serializable
object NotifyCountRoute

fun NavGraphBuilder.notifyCountScreen(
    navController: NavController,
    onShowSnackBar: (String) -> Unit,
) = composable<NotifyCountRoute> {
    NotifyCountScreenRoot(
        onShowSnackBar = onShowSnackBar,
        onGoBack = { navController.popBackStack() },
    )

}

fun NavController.navigateToNotifyCountScreen() = navigate(NotifyCountRoute)