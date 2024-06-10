package com.mohaberabi.sibha.core.presentation.compose


import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mohaberabi.sibha.R
import com.mohaberabi.sibha.core.presentation.theme.SibhaTheme

@Composable
fun ListTile(
    modifier: Modifier = Modifier,
    icon: @Composable (() -> Unit)? = null,
    text: @Composable () -> Unit,
    secondaryText: @Composable (() -> Unit)? = null,
    trailing: @Composable (() -> Unit)? = null,
    onClick: (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(enabled = onClick != null, onClick = { onClick?.invoke() })
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (icon != null) {
            icon()
            Spacer(modifier = Modifier.width(16.dp))
        }
        Column(
            modifier = Modifier.weight(1f)
        ) {
            text()
            if (secondaryText != null) {
                Spacer(modifier = Modifier.height(4.dp))
                secondaryText()
            }
        }
        if (trailing != null) {
            Spacer(modifier = Modifier.width(16.dp))
            trailing()
        }
    }
}


@Composable
fun ListTile(
    modifier: Modifier = Modifier,
    label: String,
    onClick: () -> Unit,
    @DrawableRes icon: Int
) {
    ListTile(
        onClick = onClick,
        modifier = modifier,
        text = {
            Text(text = label)
        },
        icon = {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = label
            )
        },
        trailing = {
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        },
    )

}

@Preview
@Composable
fun PreviewListItem() {
    SibhaTheme {
        ListTile(
            icon = {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = null,
                    tint = Color.Gray
                )
            },
            text = {
                Text(text = "Title")
            },
            trailing = {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = null,
                    tint = Color.Gray
                )
            },
            onClick = {

            }
        )
    }
}

@Preview
@Composable
fun PreviewListItem2() {
    SibhaTheme {

        ListTile(label = "Tasbeehs", onClick = { /*TODO*/ }, icon = R.drawable.note)
    }
}
