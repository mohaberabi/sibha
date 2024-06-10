package com.mohaberabi.sibha.features.home.presentation.screen

import android.Manifest
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mohaberabi.sibha.R
import com.mohaberabi.sibha.core.domain.model.TasbeehModel
import com.mohaberabi.sibha.core.presentation.compose.CachedImage
import com.mohaberabi.sibha.core.presentation.compose.DragToAction
import com.mohaberabi.sibha.core.presentation.compose.ObserveAsEvent
import com.mohaberabi.sibha.core.presentation.compose.SibhaAlertDialog
import com.mohaberabi.sibha.core.presentation.compose.SibhaFab
import com.mohaberabi.sibha.core.presentation.compose.SibhaScaffold
import com.mohaberabi.sibha.features.home.presentation.compose.HomeToolbar
import com.mohaberabi.sibha.features.home.presentation.compose.TasbeehOverViewShhet
import com.mohaberabi.sibha.features.home.presentation.viewmodel.HomeAction
import com.mohaberabi.sibha.features.home.presentation.viewmodel.HomeState
import com.mohaberabi.sibha.features.home.presentation.viewmodel.HomeViewModel
import com.mohaberabi.sibha.core.presentation.theme.SibhaTheme
import com.mohaberabi.sibha.core.util.hasNotificationsPermission
import com.mohaberabi.sibha.core.util.requestSibhaPermissions
import com.mohaberabi.sibha.core.util.requiresNotificationsPermission
import com.mohaberabi.sibha.core.util.shareBitmap
import com.mohaberabi.sibha.core.util.shouldShowNotificationsPermissionRationale
import com.mohaberabi.sibha.features.home.presentation.viewmodel.HomeEvent
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel(),
    onGoToSettings: () -> Unit,
    onShowSnackBar: (String) -> Unit,
) {

    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()
    ObserveAsEvent(flow = viewModel.event) { event ->
        when (event) {
            is HomeEvent.Error -> onShowSnackBar(event.error.asString(context))
            is HomeEvent.ReadyToShareImage -> shareBitmap(context, event.bitmap)
        }
    }

    HomeScreen(
        modifier = modifier,
        state = state,
        onAction = { action ->
            when (action) {
                is HomeAction.OnSettingsClick -> onGoToSettings()
                else -> {
                    viewModel.onAction(action)
                }
            }


        }
    )

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onAction: (HomeAction) -> Unit,
    state: HomeState,
) {

    val context = LocalContext.current
    val activity = context as ComponentActivity
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
    ) { perms ->
        val acceptedNotification =
            if (requiresNotificationsPermission()) {
                perms[Manifest.permission.POST_NOTIFICATIONS] == true
            } else true

        val showNotificationRational = activity.shouldShowNotificationsPermissionRationale()
        onAction(
            HomeAction.SubmitNotificaitonPermission(
                isGranted = acceptedNotification,
                showRational = showNotificationRational,
            )
        )

    }

    LaunchedEffect(true) {

        val showNotifiactionRational = activity.shouldShowNotificationsPermissionRationale()
        onAction(
            HomeAction.SubmitNotificaitonPermission(
                isGranted = context.hasNotificationsPermission(),
                showRational = showNotifiactionRational,
            )
        )

        if (!showNotifiactionRational) {
            permissionLauncher.requestSibhaPermissions(context)
        }
    }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val scope = rememberCoroutineScope()

    var isSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Box(
                contentAlignment = Alignment.CenterStart,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp)
            ) {
                HomeToolbar(
                    isSoundEnabled = state.playSound,
                    onVibrationClick = {
                        onAction(HomeAction.OnToggleVibration)
                    },
                    onResetClick = {
                        onAction(HomeAction.OnResetTasbeeh)
                    },
                    onShareClick = {
                        onAction(HomeAction.OnShareClick)
                    },
                    onSoundClick = {
                        onAction(HomeAction.OnToggleSound)
                    },
                    onMoreClick = {
                        onAction(HomeAction.OnSettingsClick)
                    },
                    isShown = true
                )
            }

        },
    ) {
        SibhaScaffold(fab = {
            SibhaFab(
                contentDesc = stringResource(R.string.clicktoopensettings),
                icon = Icons.Default.MoreVert,
                onClick = {
                    scope.launch {
                        drawerState.apply {
                            if (isClosed) open() else close()
                        }
                    }
                },
            )
        }) { padding ->
            DragToAction(

                modifier = modifier
                    .padding(padding)
                    .clickable {
                        onAction(HomeAction.OnIncrementTasbeeh)

                    },
                onDragVertical = {
                    isSheetOpen = true
                },
            ) {

                TasbeehBg(
                    tasbeeh = state.tasbeeh,
                    imageBytes = state.user.customBgBytes,
                    imageRes = state.user.background.id,
                    fontSize = state.user.fontSize
                )

                if (isSheetOpen) {
                    TasbeehOverViewShhet(
                        tasbeehs = state.tasbeehs,
                        onDismiss = { isSheetOpen = false },
                        onTasbeehClick = { tasbeeh ->
                            onAction(HomeAction.OnTasbeehClick(tasbeeh))
                        }
                    )
                }

            }

        }

    }

    if (state.showNotificationRational) {
        SibhaAlertDialog(
            title = stringResource(R.string.permission_required),
            description = stringResource(R.string.explain_noti_permission),
            positive = {
                TextButton(onClick = {
                    onAction(HomeAction.DismissRationalDialog)
                    permissionLauncher.requestSibhaPermissions(context)
                }) {
                    Text(text = "Allow")
                }
            },
            negative = {
                TextButton(
                    onClick = {
                        onAction(HomeAction.DismissRationalDialog)

                    },
                ) {
                    Text(text = "Not Now")
                }
            }
        )

    }
}

@Composable
fun TasbeehBg(
    modifier: Modifier = Modifier,
    tasbeeh: TasbeehModel?,
    imageBytes: ByteArray?,
    @DrawableRes imageRes: Int,
    fontSize: Int
) {

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {

        CachedImage(
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop,
            model = imageBytes ?: imageRes
        )
        tasbeeh?.let { tasbeeh ->
            Surface(
                color = Color.Transparent,
                tonalElevation = 4.dp
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {

                    Text(
                        text = tasbeeh.tasbeeh,
                        fontSize = fontSize.sp,
                        color = Color.White
                    )
                    Text(
                        text = tasbeeh.count.toString(), color = Color.White,
                        fontSize = 24.sp, textAlign = TextAlign.Center
                    )
                }

            }

        }

    }

}

@Preview
@Composable
private fun PreviewHomeScreen() {

    SibhaTheme {
        HomeScreen(
            onAction = {},
            state = HomeState(
                tasbeeh = TasbeehModel(tasbeeh = "Allahu Akbar", count = 20),
            )
        )

    }
}

