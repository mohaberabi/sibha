package com.mohaberabi.sibha.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.mohaberabi.sibha.features.backgrounds.presentation.navigation.backgroundsScreen
import com.mohaberabi.sibha.features.font.font_size.navigation.fontSizeScreen
import com.mohaberabi.sibha.features.home.presentation.navigation.HomeRoute
import com.mohaberabi.sibha.features.home.presentation.navigation.homeScreen
import com.mohaberabi.sibha.features.notify_count.presentation.navigation.notifyCountScreen
import com.mohaberabi.sibha.features.reminder.navigation.addReminderScreen
import com.mohaberabi.sibha.features.reminder.navigation.remindersScreen
import com.mohaberabi.sibha.features.settings.presentation.navigation.settingsScreen
import com.mohaberabi.sibha.features.tasbeeh.presentation.navigation.tasbeehScreen


@Composable
fun SibhaNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    onShowSnackBar: (String) -> Unit,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = HomeRoute,
    ) {

        homeScreen(
            navController,
            onShowSnackBar
        )
        settingsScreen(
            navController,
        )
        tasbeehScreen(
            navController,
            onShowSnackBar
        )
        backgroundsScreen(
            navController,
        )
        fontSizeScreen(
            navController,
            onShowSnackBar
        )
        notifyCountScreen(
            navController,
            onShowSnackBar
        )
        remindersScreen(
            navController,
            onShowSnackBar
        )
        addReminderScreen(
            navController,
            onShowSnackBar
        )
    }
}