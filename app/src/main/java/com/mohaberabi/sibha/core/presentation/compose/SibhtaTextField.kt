import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mohaberabi.sibha.core.presentation.theme.SibhaTheme


@Composable
fun SibhaTextField(
    modifier: Modifier = Modifier,
    hint: String = "",
    title: String? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    value: String = "",

    onChanged: (String) -> Unit,
) {

    val colors = MaterialTheme.colorScheme
    var isFocused by remember {

        mutableStateOf(false)
    }


    OutlinedTextField(
        onValueChange = onChanged,
        shape = RoundedCornerShape(30.dp),
        placeholder = {
            Text(text = hint)
        },
        value = value,
        label = {
            if (title != null)
                Text(text = title)
        },

        textStyle = LocalTextStyle.current.copy(
            color = colors.onBackground,
        ),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .onFocusChanged {
                isFocused = it.isFocused
            },

        )


}

@Preview(showBackground = true)
@Composable

fun PreivewSibhaTextField() {
    SibhaTheme {
        SibhaTextField(
            title = "Tasbeeh",
            hint = "Allahu Akbar",
            value = "",
            onChanged = {},
            modifier = Modifier.fillMaxWidth()
        )
    }
}