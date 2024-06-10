package com.mohaberabi.sibha.core.presentation.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mohaberabi.sibha.core.domain.model.TasbeehModel
import com.mohaberabi.sibha.core.presentation.theme.SibhaTheme


@Composable
fun TasbeehItem(
    modifier: Modifier = Modifier,
    tasbeeh: TasbeehModel

) {


    Row(
        modifier = modifier.padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {

        Text(
            modifier = Modifier.weight(0.8f),
            text = tasbeeh.tasbeeh,
            style = MaterialTheme.typography.titleSmall,
        )
        Text(
            text = tasbeeh.count.toString(),
            style = MaterialTheme.typography.titleSmall,
        )
    }

}


@Preview
@Composable
private fun PreviewTasbeehItem() {
    SibhaTheme {

        TasbeehItem(
            tasbeeh = TasbeehModel(
                tasbeeh = "Allahu Akbar ",
                count = 999
            )
        )
    }
}