package com.mohaberabi.sibha.core.domain.notifications

import com.mohaberabi.sibha.core.util.UiText

interface SibhaNotificationManager {


    fun show(
        title: String,
        body: String,
        id: Int = 1,
    )

    fun show(
        title: UiText,
        body: UiText,
        id: Int = 1,
    )


}