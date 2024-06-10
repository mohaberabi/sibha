package com.mohaberabi.sibha.features.backgrounds.presentation.screen

import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mohaberabi.sibha.core.util.const.SibhaBackgrounds
import com.mohaberabi.sibha.core.presentation.compose.CachedImage
import com.mohaberabi.sibha.core.presentation.compose.ObserveAsEvent
import com.mohaberabi.sibha.core.presentation.compose.SibhaAppBar
import com.mohaberabi.sibha.core.presentation.compose.SibhaScaffold
import com.mohaberabi.sibha.core.presentation.theme.SibhaTheme
import com.mohaberabi.sibha.features.backgrounds.presentation.viewmodel.BackgroundAction
import com.mohaberabi.sibha.features.backgrounds.presentation.viewmodel.BackgroundEvent
import com.mohaberabi.sibha.features.backgrounds.presentation.viewmodel.BackgroundsViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun BackGroundsScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: BackgroundsViewModel = koinViewModel(),
    onGoBack: () -> Unit,
    onGoHome: () -> Unit,
    onShowSnackbar: (String) -> Unit,
) {

    val context = LocalContext.current
    ObserveAsEvent(flow = viewModel.event) {

            event ->
        when (event) {
            BackgroundEvent.ChangedBg -> onGoHome()
            is BackgroundEvent.Error -> onShowSnackbar(event.error.asString(context))
        }
    }


    BackGroundsScreen(
        onAction = { action ->
            if (action is BackgroundAction.OnBackClick) {
                onGoBack()
            } else {
                viewModel.onAction(action)
            }

        },
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackGroundsScreen(
    modifier: Modifier = Modifier,
    onAction: (BackgroundAction) -> Unit,
) {
    val activity = LocalContext.current as ComponentActivity


    val imagePicker =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent(),
        ) { uri ->
            uri?.let {
                activity.contentResolver.openInputStream(uri)?.use {

                    onAction(BackgroundAction.OnImagePicked(it.readBytes()))
                }
            }

        }
    SibhaScaffold(
        topAppBar = {


            SibhaAppBar(
                showBackButton = true,
                title = "Backgrounds",
                actions = {

                    IconButton(onClick = {
                        imagePicker.launch("image/*")
                    }) {

                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "pickup image"
                        )

                    }
                },
                onBackClick = {
                    onAction(BackgroundAction.OnBackClick)
                },
            )
        }
    ) { paddingValues ->


        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.padding(paddingValues)
        ) {


            items(
                items = SibhaBackgrounds.entries.toList(),
                key = {
                    it.name
                }
            ) { bg ->
                CachedImage(
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(225.dp)
                        .padding(8.dp)
                        .clickable { onAction(BackgroundAction.OnBackGroundClick(bg)) }
                        .clip(RoundedCornerShape(12.dp)),
                    model = bg.id
                )
            }


        }
    }

}


@Preview
@Composable
private fun PreviewBgScreen() {
    SibhaTheme {

        BackGroundsScreen(
            onAction = {},
        )

    }
}