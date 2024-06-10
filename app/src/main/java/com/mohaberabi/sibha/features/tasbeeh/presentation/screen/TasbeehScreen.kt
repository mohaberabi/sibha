package com.mohaberabi.sibha.features.tasbeeh.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mohaberabi.sibha.R
import com.mohaberabi.sibha.core.domain.model.TasbeehModel
import com.mohaberabi.sibha.core.presentation.compose.ObserveAsEvent
import com.mohaberabi.sibha.core.presentation.compose.SibhaAppBar
import com.mohaberabi.sibha.core.presentation.compose.SibhaFab
import com.mohaberabi.sibha.core.presentation.compose.SibhaScaffold
import com.mohaberabi.sibha.core.presentation.compose.SibhaSwipeToDelete
import com.mohaberabi.sibha.core.presentation.compose.TasbeehItem
import com.mohaberabi.sibha.features.tasbeeh.presentation.viewmodel.TasbeehAction
import com.mohaberabi.sibha.features.tasbeeh.presentation.viewmodel.TasbeehEvent
import com.mohaberabi.sibha.features.tasbeeh.presentation.viewmodel.TasbeehState
import com.mohaberabi.sibha.features.tasbeeh.presentation.viewmodel.TasbeehViewModel
import com.mohaberabi.sibha.core.presentation.theme.SibhaTheme
import com.mohaberabi.sibha.features.tasbeeh.presentation.compose.AddTasbeehSheet
import org.koin.androidx.compose.koinViewModel


@Composable
fun TasbeehScreenRoot(
    modifier: Modifier = Modifier,
    onShowSnackBar: (String) -> Unit,
    onGoBack: () -> Unit,
    viewModel: TasbeehViewModel = koinViewModel(),

    ) {


    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val resetString = stringResource(id = R.string.reset)
    val deletedString = stringResource(id = R.string.deleted)
    val addedString = stringResource(id = R.string.added)

    ObserveAsEvent(flow = viewModel.event) { event ->
        when (event) {
            TasbeehEvent.Deleted -> onShowSnackBar(deletedString)
            is TasbeehEvent.Error -> onShowSnackBar(event.error.asString(context))
            TasbeehEvent.Reset -> onShowSnackBar(resetString)
            TasbeehEvent.Added -> onShowSnackBar(addedString)
        }
    }

    TasbeehScreen(
        state = state,
        onAction = { action ->
            if (action is TasbeehAction.OnBackClick) {
                onGoBack()
            } else {
                viewModel.onAction(action)
            }

        },
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasbeehScreen(
    modifier: Modifier = Modifier,
    state: TasbeehState,
    onAction: (TasbeehAction) -> Unit,
) {

    val sheetState = rememberModalBottomSheetState()
    SibhaScaffold(
        fab = {
            SibhaFab(
                icon = Icons.Default.Add,
                onClick = {
                    onAction(TasbeehAction.OnToggleAddSheet)

                },
            )
        },
        topAppBar = {
            SibhaAppBar(
                showBackButton = true, title = "Tasbeehs",
                onBackClick = {
                    onAction(TasbeehAction.OnBackClick)
                },
            )
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = modifier.padding(it)
            ) {
                items(state.tasbeehs) { tasbeeh ->
                    SibhaSwipeToDelete(
                        item = tasbeeh,
                        onDelete = {
                            onAction(TasbeehAction.OnDeleteTasbeeh(tasbeeh.id!!))

                        },
                        onUpdate = {

                            onAction(TasbeehAction.OnResetTasbeeh(tasbeeh.id!!))
                        },
                        content = {
                            TasbeehItem(tasbeeh = tasbeeh)

                        },
                        toEnd = {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                tint = MaterialTheme.colorScheme.onBackground,
                                contentDescription = "reset"
                            )
                        },
                        toStart = {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                tint = MaterialTheme.colorScheme.onBackground,
                                contentDescription = "reset"
                            )
                        },
                    )


                }
            }
            if (state.addSheetOpen) {
                ModalBottomSheet(
                    modifier = modifier,
                    sheetState = sheetState,
                    onDismissRequest = { onAction(TasbeehAction.OnToggleAddSheet) }
                ) {
                    AddTasbeehSheet(
                        loading = state.loading,
                        onChanged = {
                            onAction(TasbeehAction.OnTasbeehTextChanged(it))
                        },
                        value = state.tasbeehText,
                        onSave = { onAction(TasbeehAction.OnSaveAddedTasbeh) }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun TasbeehScreenPreview() {
    SibhaTheme {
        TasbeehScreen(
            onAction = {},
            state = TasbeehState(
                tasbeehs = listOf(
                    TasbeehModel(tasbeeh = "Allahu Akbar", count = 999),
                    TasbeehModel(tasbeeh = "Allahu Akbar", count = 999)

                )
            )
        )

    }


}