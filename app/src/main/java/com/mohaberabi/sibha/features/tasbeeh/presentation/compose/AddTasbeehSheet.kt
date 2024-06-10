package com.mohaberabi.sibha.features.tasbeeh.presentation.compose

import SibhaButton
import SibhaTextField
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mohaberabi.sibha.core.presentation.theme.SibhaTheme


@Composable
fun AddTasbeehSheet(
    modifier: Modifier = Modifier,
    loading: Boolean,
    onChanged: (String) -> Unit,
    value: String,
    onSave: () -> Unit,
) {

    Column(
        modifier = modifier.padding(8.dp),
        horizontalAlignment = Alignment.Start,
    ) {


        Text(text = "Add tasbeeh", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))
        SibhaTextField(
            value = value,
            onChanged = onChanged
        )
        Spacer(modifier = Modifier.height(16.dp))

        SibhaButton(
            label = "Add Tasbeeh",
            enabled = value.isNotEmpty(),
            isLoading = loading,
            onClick = onSave,
        )
    }

}


@Preview(showBackground = true)
@Composable
private fun PreviewAddTasbeehSheet() {

    SibhaTheme {

        AddTasbeehSheet(
            loading = false,
            onChanged = {},
            value = "allahu akbar"
        ) {


        }
    }
}