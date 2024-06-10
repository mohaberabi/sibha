package com.mohaberabi.sibha.util

import com.mohaberabi.sibha.core.util.AppResult
import com.mohaberabi.sibha.core.util.DataError


val unKnwonLocalError = AppResult.Error(DataError.Local.UNKNOWN)
val diskFullLocalError = AppResult.Error(DataError.Local.DISK_FULL)