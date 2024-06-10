package com.mohaberabi.sibha.core.domain.model

import com.mohaberabi.sibha.R
import com.mohaberabi.sibha.core.util.const.BackgroundShareType
import com.mohaberabi.sibha.core.util.const.SibhaBackgrounds
import com.mohaberabi.sibha.core.util.const.SibhaSound


data class UserPrefsModel(
    val fontSize: Int = 16,
    val background: SibhaBackgrounds = SibhaBackgrounds.LANDSCAPE_1,
    val sound: SibhaSound = SibhaSound.SOUND_1,
    val notifyCheckpoints: Set<Int> = setOf(33, 100),
    val bgShareType: BackgroundShareType = BackgroundShareType.NORMAL,
    val customBgPath: String? = null,
    val customBgBytes: ByteArray? = null
)