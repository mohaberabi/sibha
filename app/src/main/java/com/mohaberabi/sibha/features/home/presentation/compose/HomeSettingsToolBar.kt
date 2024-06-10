package com.mohaberabi.sibha.features.home.presentation.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mohaberabi.sibha.R
import com.mohaberabi.sibha.core.presentation.theme.SibhaTheme

@Composable
fun HomeToolbar(
    modifier: Modifier = Modifier,
    onSoundClick: () -> Unit = {},
    onVibrationClick: () -> Unit = {},
    onMoreClick: () -> Unit = {},
    onResetClick: () -> Unit = {},
    onShareClick: () -> Unit = {},
    isShown: Boolean = false,
    isSoundEnabled: Boolean = true,
    isVibrationEnabled: Boolean = true,
) {

    AnimatedVisibility(

        visible = isShown, enter = fadeIn(), exit = fadeOut(),
    ) {
        Column(
            modifier = modifier
                .clip(RoundedCornerShape(16.dp))
                .background(Color.Black.copy(alpha = 0.6f))
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ToolBarTile(
                onClick = onSoundClick,
                label = "Sound",
                icon = painterResource(id =if(isSoundEnabled) R.drawable.music_on else R.drawable.music_off),
            )
            ToolBarTile(
                onClick = onVibrationClick,
                label = "Vibration",
                icon = painterResource(id = R.drawable.vibration),
            )
            ToolBarTile(
                onClick = onResetClick,
                label = "Reset",
                icon = painterResource(id = R.drawable.restart),
            )
            ToolBarTile(
                onClick = onShareClick,
                label = "Share",
                icon = painterResource(id = R.drawable.share),
            )

            ToolBarTile(
                onClick = onMoreClick,
                label = "More",
                icon = painterResource(id = R.drawable.more),
            )

        }
    }


}


@Composable
fun ToolBarTile(
    onClick: () -> Unit,
    label: String,
    icon: Painter,
    modifier: Modifier = Modifier
) {

    Column(
        modifier =
        modifier.clickable {
            onClick()
        },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(28.dp))
        Icon(
            painter = icon,
            tint = Color.White,
            contentDescription = label,
            modifier = Modifier.size(16.dp)
        )
        Text(
            text = label,
            color = Color.White,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
        )
    }

}

@Preview(
    showBackground = false,
)
@Composable
private fun PreviewToolBar() {
    SibhaTheme {
        HomeToolbar()
    }
}