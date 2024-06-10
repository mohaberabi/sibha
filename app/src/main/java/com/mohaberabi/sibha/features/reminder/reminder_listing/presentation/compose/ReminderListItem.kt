package com.mohaberabi.sibha.features.reminder.reminder_listing.presentation.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mohaberabi.sibha.R
import com.mohaberabi.sibha.core.domain.model.ReminderModel
import com.mohaberabi.sibha.core.presentation.theme.SibhaTheme


@Composable
fun ReminderListItem(
    modifier: Modifier = Modifier,
    onCheckChanged: (Boolean) -> Unit = {},
    onDelete: () -> Unit = {},
    reminder: ReminderModel,
) {


    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Column(
            horizontalAlignment = Alignment.Start,
        ) {


            Text(
                text = reminder.title,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            )

            Text(
                text = reminder.body,
                style = MaterialTheme.typography.bodyMedium,
            )

            TextButton(
                onClick = onDelete,
            ) {
                Text(
                    text = stringResource(R.string.delete),
                    style = MaterialTheme.typography.bodySmall.copy(color = Color.Red)
                )
            }
        }
        Column(
            horizontalAlignment = Alignment.End,
        ) {


            Text(
                text = "${reminder.hour}:${reminder.minute}",
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
            )

            Switch(
                checked = reminder.enabled,
                onCheckedChange = onCheckChanged,
            )

        }
    }

}


@Preview(showBackground = true)
@Composable
private fun PreviewReminderListItem() {

    SibhaTheme {

        ReminderListItem(
            reminder = ReminderModel(
                title = "Tasbeeh Reminder",
                body = "Do not forget your daily tasbeeh",
                minute = 30,
                hour = 12
            )
        )
    }
}