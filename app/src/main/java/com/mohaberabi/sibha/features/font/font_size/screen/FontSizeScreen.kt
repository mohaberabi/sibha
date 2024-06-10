package com.mohaberabi.sibha.features.font.font_size.screen

import SibhaButton
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mohaberabi.sibha.R
import com.mohaberabi.sibha.core.util.const.SibhaConst
import com.mohaberabi.sibha.core.presentation.compose.ObserveAsEvent
import com.mohaberabi.sibha.core.presentation.compose.SibhaAppBar
import com.mohaberabi.sibha.core.presentation.compose.SibhaScaffold
import com.mohaberabi.sibha.core.presentation.theme.SibhaTheme
import com.mohaberabi.sibha.features.font.font_size.viewmodel.FontSizeAction
import com.mohaberabi.sibha.features.font.font_size.viewmodel.FontSizeEvent
import com.mohaberabi.sibha.features.font.font_size.viewmodel.FontSizeState
import com.mohaberabi.sibha.features.font.font_size.viewmodel.FontSizeViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun FontSizeScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: FontSizeViewModel = koinViewModel(),
    onShowSnackBar: (String) -> Unit,
    onGoBack: () -> Unit
) {

    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()
    ObserveAsEvent(flow = viewModel.event) { event ->
        when (event) {
            is FontSizeEvent.Error -> onShowSnackBar(event.error.asString(context))
            FontSizeEvent.Saved -> onShowSnackBar(context.getString(R.string.font_size_saved_successfully))
        }
    }
    FontSizeScreen(
        modifier = modifier,
        state = state,
        onAction = { action ->
            if (action is FontSizeAction.OnGoBack) {
                onGoBack()
            } else {
                viewModel.onAction(action)
            }
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FontSizeScreen(
    modifier: Modifier = Modifier,
    state: FontSizeState,
    onAction: (FontSizeAction) -> Unit
) {
    val sliderPositions by remember { mutableStateOf(16f..48f) }

    val fontSizeValue by remember {

        mutableFloatStateOf(state.fontSize.toFloat())
    }
    val sliderState = remember {
        SliderState(
            onValueChangeFinished = {

            },
            value = fontSizeValue,
            valueRange = sliderPositions
        )
    }
    SibhaScaffold(
        modifier = modifier,
        topAppBar = {
            SibhaAppBar(
                onBackClick = {
                    onAction(FontSizeAction.OnGoBack)
                },
                showBackButton = true,
                title = "Font Size",
            )
        }
    ) {


        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier =
            Modifier
                .padding(16.dp)
                .fillMaxSize(),
        ) {

            Text(
                text = SibhaConst.DEFAULT_FONT_PREVIEW,
                style = MaterialTheme.typography.headlineLarge,
                fontSize = sliderState.value.sp
            )

            Slider(
                state = sliderState,
            )


            SibhaButton(
                label = "Save",
                isLoading = state.loading,
                onClick = {
                    onAction(FontSizeAction.OnSaveFontSize(sliderState.value.toInt()))
                },
            )

        }
    }
}


@Preview
@Composable
private fun PreviewFontSizeScreen() {

    SibhaTheme {

        FontSizeScreen(
            state = FontSizeState(),
            onAction = {},
        )

    }
}