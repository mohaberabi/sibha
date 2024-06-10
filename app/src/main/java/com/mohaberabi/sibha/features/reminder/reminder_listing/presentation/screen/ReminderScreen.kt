package com.mohaberabi.sibha.features.reminder.reminder_listing.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mohaberabi.sibha.R
import com.mohaberabi.sibha.core.presentation.compose.ObserveAsEvent
import com.mohaberabi.sibha.core.presentation.compose.SibhaAppBar
import com.mohaberabi.sibha.core.presentation.compose.SibhaFab
import com.mohaberabi.sibha.core.presentation.compose.SibhaPlaceHolder
import com.mohaberabi.sibha.core.presentation.compose.SibhaScaffold
import com.mohaberabi.sibha.core.presentation.theme.SibhaTheme
import com.mohaberabi.sibha.features.reminder.reminder_listing.presentation.compose.ReminderListItem
import com.mohaberabi.sibha.features.reminder.reminder_listing.presentation.viewmodel.ReminderEvent
import com.mohaberabi.sibha.features.reminder.reminder_listing.presentation.viewmodel.ReminderState
import com.mohaberabi.sibha.features.reminder.reminder_listing.presentation.viewmodel.RemindersAction
import com.mohaberabi.sibha.features.reminder.reminder_listing.presentation.viewmodel.RemindersViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun ReminderScreenRoot(
    modifier: Modifier = Modifier,
    onGoBack: () -> Unit,
    onGoAddReminder: () -> Unit,
    onShowSnackBar: (String) -> Unit,
    viewModel: RemindersViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    ObserveAsEvent(flow = viewModel.event) { event ->
        when (event) {
            is ReminderEvent.Error -> onShowSnackBar(event.error.asString(context))
        }
    }
    ReminderScreen(
        modifier = modifier,

        state = state,
        onAction = { action ->
            when (action) {
                RemindersAction.OnAddReminder -> onGoAddReminder()
                RemindersAction.OnGoBackClick -> onGoBack()
                else -> viewModel.onAction(action)
            }
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReminderScreen(
    modifier: Modifier = Modifier,
    onAction: (RemindersAction) -> Unit,
    state: ReminderState,
) {

    SibhaScaffold(
        modifier = modifier,
        topAppBar = {
            SibhaAppBar(
                showBackButton = true,
                title = "Reminders", onBackClick = { onAction(RemindersAction.OnGoBackClick) },
            )
        },
        fab = {
            SibhaFab(
                icon = Icons.Default.Add,
                onClick = {
                    onAction(RemindersAction.OnAddReminder)
                },
            )
        },
    ) {

            padding ->
        if (state.reminders.isEmpty()) {

            SibhaPlaceHolder(
                modifier = Modifier.padding(20.dp),
                title = stringResource(R.string.you_do_not_have_any_reminders_added_yet),
                action = {
                    onAction(RemindersAction.OnAddReminder)
                },
            )
        } else {
            LazyColumn(modifier = Modifier.padding(padding)) {


                item {
                    Text(
                        text = stringResource(R.string.reminder_will_be_repeated_everyday_at_chosen_time),
                        style = MaterialTheme.typography.titleMedium.copy(color = Color.Gray),
                    )
                }

                item {
                    HorizontalDivider(
                        thickness = 0.3.dp,
                        color = Color.LightGray
                    )
                }
                items(state.reminders) { reminder ->
                    ReminderListItem(
                        reminder = reminder,
                        onDelete = {
                            onAction(RemindersAction.OnDeleteReminder(reminder.id!!))
                        },
                        onCheckChanged = {

                            onAction(RemindersAction.OnUpdateReminder(reminder.copy(enabled = it)))
                        }
                    )
                }
            }
        }

    }
}


@Preview(showBackground = true)
@Composable
private fun PreviewReminderScreen() {

    SibhaTheme {

        ReminderScreen(
            state = ReminderState(),
            onAction = {}
        )
    }
}