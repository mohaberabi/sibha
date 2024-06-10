package com.mohaberabi.sibha.features.font.font_size.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.mohaberabi.sibha.features.font.font_size.screen.FontSizeScreenRoot
import kotlinx.serialization.Serializable


@Serializable
object FontSizeRoute


fun NavGraphBuilder.fontSizeScreen(
    navHostController: NavHostController,
    onShowSnackBar: (String) -> Unit,
) = composable<FontSizeRoute> {
    FontSizeScreenRoot(
        onShowSnackBar = onShowSnackBar,
        onGoBack = { navHostController.popBackStack() }
    )
}

fun NavController.navigateToFontSizeScreen() = navigate(FontSizeRoute)