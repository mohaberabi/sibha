package com.mohaberabi.sibha.features.settings.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mohaberabi.sibha.features.backgrounds.presentation.navigation.navigateToBackgrounds
import com.mohaberabi.sibha.features.font.font_size.navigation.navigateToFontSizeScreen
import com.mohaberabi.sibha.features.notify_count.presentation.navigation.navigateToNotifyCountScreen
import com.mohaberabi.sibha.features.reminder.navigation.navigateToRemindersScreen
import com.mohaberabi.sibha.features.settings.presentation.screen.SettingsScreen
import com.mohaberabi.sibha.features.tasbeeh.presentation.navigation.navigateToTasbeehScreen
import kotlinx.serialization.Serializable


@Serializable
object SettingsRoute


fun NavGraphBuilder.settingsScreen(
    navController: NavController,
) {
    composable<SettingsRoute> {
        SettingsScreen(
            onTasbeehClick = { navController.navigateToTasbeehScreen() },
            onBackgroundsClick = { navController.navigateToBackgrounds() },
            onFontSizeClick = { navController.navigateToFontSizeScreen() },
            onBackClick = { navController.popBackStack() },
            onRemindersClick = { navController.navigateToNotifyCountScreen() },
            onNotificationsClick = { navController.navigateToRemindersScreen() }
        )
    }
}

fun NavController.navigateToSettings() = navigate(SettingsRoute)