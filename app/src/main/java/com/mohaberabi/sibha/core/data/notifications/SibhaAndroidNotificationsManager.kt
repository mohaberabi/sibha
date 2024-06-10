package com.mohaberabi.sibha.core.data.notifications

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.content.getSystemService
import com.mohaberabi.sibha.R
import com.mohaberabi.sibha.core.domain.notifications.SibhaNotiConst
import com.mohaberabi.sibha.core.domain.notifications.SibhaNotificationManager
import com.mohaberabi.sibha.core.util.UiText

class SibhaAndroidNotificationsManager(
    private val context: Context,
) : SibhaNotificationManager {


    private val notificationManager = context.getSystemService<NotificationManager>()!!
    override fun show(
        title: String,
        body: String,
        id: Int
    ) = showSimpleNotificaiton(title = title, body = body, id = id)

    override fun show(title: UiText, body: UiText, id: Int) = showSimpleNotificaiton(
        title = title.asString(context),
        body = body.asString(context),
        id = id
    )

    private val sibhaNotification = NotificationCompat.Builder(
        context,
        SibhaNotiConst.SIBHA_DEFAULT_CHANNEL,
    ).setSmallIcon(R.drawable.ic_launcher_foreground)


    private fun showSimpleNotificaiton(
        title: String, body: String, id: Int,
    ) {
        val notification = sibhaNotification
            .setContentText(body)
            .setContentTitle(title)
            .build()
        notificationManager.notify(
            id,
            notification
        )
    }
}