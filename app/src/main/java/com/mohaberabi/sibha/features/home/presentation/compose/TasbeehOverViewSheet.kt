package com.mohaberabi.sibha.features.home.presentation.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mohaberabi.sibha.core.domain.model.TasbeehModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasbeehOverViewShhet(
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(),
    tasbeehs: List<TasbeehModel>,
    onDismiss: () -> Unit,
    onTasbeehClick: (TasbeehModel) -> Unit = {}
) {


    ModalBottomSheet(
        modifier = modifier,
        sheetState = sheetState,
        onDismissRequest = onDismiss
    ) {

        LazyColumn(contentPadding = PaddingValues(20.dp)) {


            items(tasbeehs) { tasbeeh ->
                Text(

                    text = tasbeeh.tasbeeh,
                    modifier = Modifier
                        .clickable {
                            onTasbeehClick(tasbeeh)
                            onDismiss()
                        }
                        .padding(16.dp)
                )
            }
        }
    }
}
