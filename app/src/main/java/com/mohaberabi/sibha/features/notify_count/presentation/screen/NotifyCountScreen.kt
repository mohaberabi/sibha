package com.mohaberabi.sibha.features.notify_count.presentation.screen

import SibhaButton
import SibhaTextField
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mohaberabi.sibha.R
import com.mohaberabi.sibha.core.presentation.compose.ObserveAsEvent
import com.mohaberabi.sibha.core.presentation.compose.SibhaAppBar
import com.mohaberabi.sibha.core.presentation.compose.SibhaFab
import com.mohaberabi.sibha.core.presentation.compose.SibhaPlaceHolder
import com.mohaberabi.sibha.core.presentation.compose.SibhaScaffold
import com.mohaberabi.sibha.core.presentation.compose.SibhaSwipeToDelete
import com.mohaberabi.sibha.core.presentation.theme.SibhaTheme
import com.mohaberabi.sibha.features.notify_count.presentation.viewmodel.NotifiyCountAction
import com.mohaberabi.sibha.features.notify_count.presentation.viewmodel.NotifiyCountState
import com.mohaberabi.sibha.features.notify_count.presentation.viewmodel.NotifyCountEvent
import com.mohaberabi.sibha.features.notify_count.presentation.viewmodel.NotifyCountViewModel
import com.mohaberabi.sibha.sibha_app.SibhaApp
import org.koin.androidx.compose.koinViewModel
import kotlin.math.acos


@Composable
fun NotifyCountScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: NotifyCountViewModel = koinViewModel(),
    onShowSnackBar: (String) -> Unit,
    onGoBack: () -> Unit
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    val added = stringResource(id = R.string.added)
    val deleted = stringResource(id = R.string.deleted)
    val context = LocalContext.current
    ObserveAsEvent(flow = viewModel.event) { event ->
        when (event) {
            NotifyCountEvent.Deleted -> onShowSnackBar(deleted)
            is NotifyCountEvent.Error -> onShowSnackBar(event.error.asString(context))
            NotifyCountEvent.Saved -> onShowSnackBar(added)
        }
    }
    NotifyCountScreen(state = state, onAction = { action ->
        if (action is NotifiyCountAction.OnBackClick) {
            onGoBack()
        } else {
            viewModel.onAction(action)
        }
    })
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotifyCountScreen(
    modifier: Modifier = Modifier,
    state: NotifiyCountState,
    onAction: (NotifiyCountAction) -> Unit,
) {


    SibhaScaffold(
        modifier = modifier,

        fab = {

            if (state.notifiyCount.isNotEmpty())
                SibhaFab(
                    icon = Icons.Default.Add,
                    onClick = {
                        onAction(NotifiyCountAction.ToggleAddDialog)
                    },
                )
        },
        topAppBar = {
            SibhaAppBar(
                showBackButton = true,
                onBackClick = { onAction(NotifiyCountAction.OnBackClick) },
                title = "Notify on count"
            )
        }
    ) { padding ->


        Box(modifier = Modifier.padding(padding)) {
            Column(
                modifier = Modifier
                    .padding(16.dp),
            ) {

                Text(
                    text = stringResource(R.string.notify_count_explain),
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = Color.LightGray
                    )
                )


                if (state.notifiyCount.isNotEmpty()) {

                    LazyColumn {

                        items(state.notifiyCount.toList()) {

                                count ->
                            SibhaSwipeToDelete(
                                item = count,
                                onDelete = {
                                    onAction(NotifiyCountAction.OnCountDelete(count))
                                },
                                content = {
                                    Text(
                                        text = count.toString(),
                                        style = MaterialTheme.typography.titleLarge,
                                        modifier = Modifier
                                            .padding(20.dp)
                                            .fillMaxWidth()
                                    )
                                },
                                toStart = {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        tint = MaterialTheme.colorScheme.onBackground,
                                        contentDescription = "delete"
                                    )
                                },
                            )

                        }
                    }
                } else {
                    SibhaPlaceHolder(
                        title = "You don't have any reminder count yet",
                        subtitle = "Start adding now ",
                        action = { onAction(NotifiyCountAction.ToggleAddDialog) }
                    )
                }


            }
            if (state.addDialogShown)
                ModalBottomSheet(

                    onDismissRequest = {
                        onAction(NotifiyCountAction.ToggleAddDialog)
                    },
                ) {

                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Add Notify Count",
                            style = MaterialTheme.typography.headlineMedium,
                        )
                        SibhaTextField(
                            keyboardType = KeyboardType.Number,
                            value = state.count.toString(),
                            onChanged = {

                                if (it.isNotEmpty()) {
                                    onAction(NotifiyCountAction.OnCountChanged(it.toInt()))
                                }
                            },
                        )

                        SibhaButton(
                            isLoading = state.loading,
                            label = "Save",
                            enabled = state.count > 0,
                            onClick = {
                                onAction(NotifiyCountAction.OnSaveCount)
                            }
                        )
                    }


                }
        }

    }

}


@Preview
@Composable
private fun PreviewNotifyCountScreen() {

    SibhaTheme {

        NotifyCountScreen(
            state = NotifiyCountState(),
            onAction = {},
        )

    }
}