package com.mohaberabi.sibha.core.util

import com.mohaberabi.sibha.R


fun DataError.asUiText(): UiText {

    return when (this) {
        DataError.Local.DISK_FULL -> UiText.StringResource(R.string.disk_full)
        DataError.Local.IOEXCEPTION -> UiText.StringResource(R.string.error_io)

        else -> UiText.StringResource(R.string.unknown_error)
    }
}