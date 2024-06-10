package com.mohaberabi.sibha.core.presentation.compose

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput


@Composable
fun DragToAction(
    modifier: Modifier = Modifier,
    onDragVertical: () -> Unit,
    onDragHorizontal: (() -> Unit)? = null,

    contentAlignment: Alignment = Alignment.Center,
    content: @Composable BoxScope.() -> Unit,

    ) {
    var offsetY by remember { mutableStateOf(0f) }
    var offsetX by remember { mutableStateOf(0f) }

    Box(
        contentAlignment = contentAlignment,
        modifier = modifier
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    val newOffsetY = offsetY + dragAmount.y
                    val newOffsetX = offsetX + dragAmount.x

                    if (newOffsetY >= 0) {
                        offsetY = newOffsetY
                        val isDraggedUpwards = dragAmount.y < 0
                        if (isDraggedUpwards) {
                            onDragVertical()
                        }
                    }

                    if (onDragHorizontal != null) {
                        if (newOffsetX >= 0) {
                            offsetX = newOffsetX
                            val isDragFromLeft = dragAmount.x < 0
                            if (isDragFromLeft) {
                                onDragHorizontal()
                            }
                        }
                    }


                }
            }

    ) {
        content()
    }
}