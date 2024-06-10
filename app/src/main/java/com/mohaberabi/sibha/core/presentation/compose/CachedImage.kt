package com.mohaberabi.sibha.core.presentation.compose

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage

@Composable
fun CachedImage(
    modifier: Modifier = Modifier,
    model: Any?,
    contentScale: ContentScale = ContentScale.Crop
) {


    SubcomposeAsyncImage(
        error = {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = "", tint = Color.Gray
            )
        },
        loading = {
            SibhaLoader()
        },
        modifier = modifier,
        model = model,
        contentDescription = "",
        contentScale = contentScale
    )
}