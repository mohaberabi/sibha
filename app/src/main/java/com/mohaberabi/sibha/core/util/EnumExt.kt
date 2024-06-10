package com.mohaberabi.sibha.core.util

import kotlin.enums.enumEntries

@OptIn(ExperimentalStdlibApi::class)
inline fun <reified T : Enum<T>> String.toEnum(ignoreCase: Boolean = true): T? {
    return enumEntries<T>().find { it.name.equals(this, ignoreCase = ignoreCase) }
}