package com.mohaberabi.sibha.features.reminder.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mohaberabi.sibha.features.reminder.add_reminder.screen.AddReminderScreenRoot
import com.mohaberabi.sibha.features.reminder.reminder_listing.presentation.screen.ReminderScreenRoot
import kotlinx.serialization.Serializable


@Serializable
object AddReminderRoute

@Serializable
object ReminderListingRoute


fun NavGraphBuilder.addReminderScreen(
    navController: NavController,
    onShowSnackBar: (String) -> Unit,
) = composable<AddReminderRoute> {
    AddReminderScreenRoot(
        onShowSnackBar = onShowSnackBar,
        onGoBack = { navController.popBackStack() })

}


fun NavGraphBuilder.remindersScreen(
    navController: NavController,
    onShowSnackBar: (String) -> Unit,

    ) = composable<ReminderListingRoute> {
    ReminderScreenRoot(
        onGoAddReminder = { navController.navigateToAddReminderScreen() },
        onGoBack = { navController.popBackStack() },
        onShowSnackBar = onShowSnackBar
    )

}


fun NavController.navigateToAddReminderScreen() = navigate(AddReminderRoute)
fun NavController.navigateToRemindersScreen() = navigate(ReminderListingRoute)