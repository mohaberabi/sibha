package com.mohaberabi.sibha.core.presentation.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mohaberabi.sibha.core.presentation.theme.SibhaTheme
import kotlinx.coroutines.delay


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DismissBackGround(
    modifier: Modifier = Modifier,
    dismissState: SwipeToDismissBoxState,
    toStart: @Composable () -> Unit,
    toEnd: (@Composable () -> Unit)? = null,
    toEndColor: Color = Color.Yellow,
    toStartColor: Color = Color.Red
) {
    val direction = dismissState.dismissDirection


    when (direction) {
        SwipeToDismissBoxValue.StartToEnd -> {
            Box(
                contentAlignment = Alignment.CenterStart,
                modifier = Modifier
                    .fillMaxSize()
                    .background(toEndColor)
            ) {
                if(toEnd!=null)
                toEnd()
            }

        }

        SwipeToDismissBoxValue.EndToStart -> {
            Box(
                contentAlignment = Alignment.CenterEnd,

                modifier = Modifier
                    .fillMaxSize()
                    .background(toStartColor)
            ) {
                toStart()
            }
        }

        SwipeToDismissBoxValue.Settled -> Unit
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> SibhaSwipeToDelete(
    item: T,
    onDelete: (T) -> Unit,
    onUpdate: ((T) -> Unit)? = null,
    animationDuration: Int = 500,
    content: @Composable (T) -> Unit,
    toEnd: (@Composable () -> Unit)? = null,
    toStart: @Composable () -> Unit,
) {
    var isRemoved by remember {
        mutableStateOf(false)
    }
    var isUpdated by remember {
        mutableStateOf(false)
    }
    val state = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            when (value) {
                SwipeToDismissBoxValue.StartToEnd -> false
                SwipeToDismissBoxValue.EndToStart -> {
                    isRemoved = true
                    true
                }

                SwipeToDismissBoxValue.Settled -> false
            }

        }
    )

    LaunchedEffect(key1 = isRemoved, key2 = isUpdated) {
        if (isRemoved) {
            delay(animationDuration.toLong())
            onDelete(item)
        }
        if (isUpdated) {
            if (onUpdate != null) {
                delay(animationDuration.toLong())
                onUpdate(item)
            }

        }
    }

    AnimatedVisibility(
        visible = !isRemoved,
        exit = shrinkVertically(
            animationSpec = tween(durationMillis = animationDuration),
            shrinkTowards = Alignment.Top
        ) + fadeOut()
    ) {

        SwipeToDismissBox(
            state = state,
            content = { content(item) },
            backgroundContent = {
                DismissBackGround(
                    dismissState = state,
                    toEnd = toEnd,
                    toStart = toStart,
                )
            },
        )
    }
}


@Preview(
)
@Composable
private fun PreviwSibhaSwipteToDelete() {
    SibhaTheme {

        SibhaSwipeToDelete<String>(
            item = "Kotlin",
            onDelete = {

            },
            onUpdate = {},
            content = {
                Text(text = it)
            },
            toEnd = {
                Surface(color = Color.Red) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        tint = MaterialTheme.colorScheme.onBackground,
                        contentDescription = "delete"
                    )
                }
            },
            toStart = {
                Surface(color = Color.Red) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        tint = MaterialTheme.colorScheme.onBackground,
                        contentDescription = "delete"
                    )
                }
            },
        )
    }
}