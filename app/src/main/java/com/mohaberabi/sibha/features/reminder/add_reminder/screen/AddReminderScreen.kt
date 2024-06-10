package com.mohaberabi.sibha.features.reminder.add_reminder.screen

import SibhaButton
import SibhaTextField
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mohaberabi.sibha.R
import com.mohaberabi.sibha.core.presentation.compose.ObserveAsEvent
import com.mohaberabi.sibha.core.presentation.compose.SibhaAppBar
import com.mohaberabi.sibha.core.presentation.compose.SibhaScaffold
import com.mohaberabi.sibha.core.presentation.compose.SibhaTimePicker
import com.mohaberabi.sibha.core.presentation.theme.SibhaTheme
import com.mohaberabi.sibha.features.reminder.add_reminder.viewmodel.AddReminderAction
import com.mohaberabi.sibha.features.reminder.add_reminder.viewmodel.AddReminderEvent
import com.mohaberabi.sibha.features.reminder.add_reminder.viewmodel.AddReminderState
import com.mohaberabi.sibha.features.reminder.add_reminder.viewmodel.AddReminderViewModel
import com.mohaberabi.sibha.features.reminder.add_reminder.viewmodel.canAdd
import org.koin.androidx.compose.koinViewModel


@Composable
fun AddReminderScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: AddReminderViewModel = koinViewModel(),
    onShowSnackBar: (String) -> Unit,
    onGoBack: () -> Unit,
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    ObserveAsEvent(flow = viewModel.event) { event ->
        when (event) {
            AddReminderEvent.Added -> {
                onShowSnackBar(context.getString(R.string.added))
                onGoBack()
            }

            is AddReminderEvent.Error -> onShowSnackBar(event.error.asString(context))
        }
    }

    AddReminderScreen(
        modifier = modifier,
        onAction = { action ->
            if (action is AddReminderAction.OnGoBackClick) {
                onGoBack()
            } else {
                viewModel.onAction(action)
            }

        },
        state = state
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddReminderScreen(
    modifier: Modifier = Modifier,
    onAction: (AddReminderAction) -> Unit,
    state: AddReminderState,
) {

    SibhaScaffold(topAppBar = {
        SibhaAppBar(
            showBackButton = true,
            title = "Add reminder",
            onBackClick = { onAction(AddReminderAction.OnGoBackClick) })
    }) {


            padding ->
        Column(
            modifier = modifier
                .padding(padding)
                .padding(16.dp)
                .scrollable(rememberScrollState(), orientation = Orientation.Vertical),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {

            SibhaTimePicker(
                onTimeChanged = { result ->
                    onAction(AddReminderAction.OnTimeChanged(result))
                }
            )

            SibhaTextField(
                title = stringResource(R.string.name),
                value = state.title,
                onChanged = {
                    onAction(AddReminderAction.OnTitleChanged(it))
                },
            )
            SibhaTextField(
                value = state.body,
                title = stringResource(R.string.description),
                onChanged = {
                    onAction(AddReminderAction.OnBodyChanged(it))
                },
            )
            SibhaButton(
                isLoading = state.isLoading,
                onClick = {
                    onAction(AddReminderAction.OnSaveReminder)
                },
                enabled = state.canAdd,
                label = stringResource(id = R.string.add)
            )
        }

    }

}

@Preview
@Composable
private fun PreviewAddReminderScreen() {
    SibhaTheme {


        AddReminderScreen(
            onAction = {},
            state = AddReminderState(),
        )
    }
}