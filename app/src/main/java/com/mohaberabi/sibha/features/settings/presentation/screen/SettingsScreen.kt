package com.mohaberabi.sibha.features.settings.presentation.screen

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.mohaberabi.sibha.R
import com.mohaberabi.sibha.core.util.const.FunUnit
import com.mohaberabi.sibha.core.presentation.compose.ListTile
import com.mohaberabi.sibha.core.presentation.compose.SibhaAppBar
import com.mohaberabi.sibha.core.presentation.compose.SibhaScaffold
import com.mohaberabi.sibha.core.presentation.theme.SibhaTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    onTasbeehClick: FunUnit = {},
    onBackgroundsClick: FunUnit = {},
    onFontSizeClick: FunUnit = {},
    onRemindersClick: FunUnit = {},
    onNotificationsClick: FunUnit = {},
    onBackClick: () -> Unit = {},
) {
    val scrollState = rememberScrollState()
    SibhaScaffold(topAppBar = {
        SibhaAppBar(
            showBackButton = true, title = stringResource(R.string.settings),
            onBackClick = onBackClick
        )
    }) {
        Column(
            modifier = modifier
                .scrollable(
                    state = scrollState,
                    orientation = Orientation.Vertical,
                )
                .padding(it)
        ) {

            ListTile(
                label = "Tasbeehs", onClick = onTasbeehClick,
                icon = R.drawable.note
            )
            ListTile(
                label = "Backgrounds", onClick = onBackgroundsClick,
                icon = R.drawable.image
            )

            ListTile(
                label = "Font Size", onClick = onFontSizeClick,
                icon = R.drawable.font_size
            )
            ListTile(
                label = "Reminders", onClick = onRemindersClick,
                icon = R.drawable.notfict
            )
            ListTile(
                label = "Notifications", onClick = onNotificationsClick,
                icon = R.drawable.notfict
            )

        }
    }

}


@Preview
@Composable
private fun SettingsScreenPreview() {


    SibhaTheme {
        SettingsScreen()
    }
}