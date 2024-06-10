package com.mohaberabi.sibha.core.presentation.compose

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class SibhaTimePickerResult(
    val hour: Int = 12,
    val minute: Int = 0,
    val is24: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SibhaTimePicker(
    modifier: Modifier = Modifier,
    onTimeChanged: (SibhaTimePickerResult) -> Unit,
) {

    val state = rememberTimePickerState()

    TimePicker(
        state = state,
        modifier = modifier.padding(12.dp),
        colors = TimePickerDefaults.colors(
            clockDialColor = MaterialTheme.colorScheme.primary,
            clockDialSelectedContentColor = MaterialTheme.colorScheme.primary,
            selectorColor = MaterialTheme.colorScheme.onPrimary,
            clockDialUnselectedContentColor = MaterialTheme.colorScheme.secondary,
            periodSelectorBorderColor = MaterialTheme.colorScheme.secondary,

            containerColor = MaterialTheme.colorScheme.primary,
            timeSelectorSelectedContentColor = MaterialTheme.colorScheme.primary,
            timeSelectorUnselectedContainerColor = MaterialTheme.colorScheme.secondary,
        )
    )
    LaunchedEffect(state.hour, state.minute, state.is24hour) {
        onTimeChanged(
            SibhaTimePickerResult(
                hour = state.hour,
                minute = state.minute,
                is24 = state.is24hour
            )
        )
    }

}