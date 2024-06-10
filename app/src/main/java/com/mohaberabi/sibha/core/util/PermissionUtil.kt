package com.mohaberabi.sibha.core.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat


fun requiresNotificationsPermission(): Boolean = Build.VERSION.SDK_INT >= 33
fun ComponentActivity.shouldShowNotificationsPermissionRationale(): Boolean =
    requiresNotificationsPermission() &&
            shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)

fun Context.hasPermission(permission: String): Boolean =
    ContextCompat.checkSelfPermission(
        this,
        permission,
    ) == PackageManager.PERMISSION_GRANTED


fun Context.hasNotificationsPermission(): Boolean =
    if (requiresNotificationsPermission()) hasPermission(Manifest.permission.POST_NOTIFICATIONS) else true


fun ActivityResultLauncher<Array<String>>.requestSibhaPermissions(
    context: Context
) {
    val hasNotification = context.hasNotificationsPermission()

    val notificationPermission = if (requiresNotificationsPermission()) {
        arrayOf(Manifest.permission.POST_NOTIFICATIONS)
    } else arrayOf()

    when {
        !hasNotification -> launch(notificationPermission)
    }
}

