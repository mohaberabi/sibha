package com.mohaberabi.sibha.core.presentation.compose

import SibhaButton
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mohaberabi.sibha.R


@Composable
fun SibhaPlaceHolder(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String? = null,
    action: () -> Unit = {},
    actionLabel: String = stringResource(id = R.string.add)
) {


    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {


        Icon(
            imageVector = Icons.Default.Info,
            contentDescription = null, tint = Color.LightGray,
            modifier = Modifier.size(75.dp)
        )
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(8.dp))

        if (subtitle != null) {
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray),
                textAlign = TextAlign.Center,
            )
        }


        Spacer(modifier = Modifier.height(20.dp))

        SibhaButton(
            label = actionLabel,
            onClick = action,
            enabled = true
        )
    }
}