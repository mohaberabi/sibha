package com.mohaberabi.sibha.core.util.const

import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import com.mohaberabi.sibha.R
import com.mohaberabi.sibha.core.domain.model.TasbeehModel


object SibhaConst {
    val defaultTasbeehs = listOf<TasbeehModel>(

        TasbeehModel(tasbeeh = "Subhan Allah", count = 0),
        TasbeehModel(tasbeeh = "Alhamdu Li Allah", count = 0),
        TasbeehModel(tasbeeh = "Allahu Akbar", count = 0)

    )


    const val DEFAULT_FONT_PREVIEW = "سبحان الله"
}

enum class BackgroundShareType {
    NORMAL, WHITE, BLACK
}

enum class SibhaSound(@RawRes val id: Int) {
    SOUND_1(R.raw.sound)
}

enum class SibhaBackgrounds(
    @DrawableRes val id: Int,
) {
    LANDSCAPE_1(R.drawable.land1),
    LANDSCAPE_2(R.drawable.land2),
    LANDSCAPE_3(R.drawable.land3),
    LANDSCAPE_4(R.drawable.land4),
    LANDSCAPE_5(R.drawable.land5),
}