package com.mohaberabi.sibha.core.util

import com.google.common.truth.Truth
import org.junit.jupiter.api.Test

class DataErrorToTextTest {


    @Test
    fun `when data error object is converted to uitext should return the correct one `() {
        val error = DataError.Local.DISK_FULL
        Truth.assertThat(error.asUiText()).isInstanceOf(UiText.StringResource::class.java)
    }
}