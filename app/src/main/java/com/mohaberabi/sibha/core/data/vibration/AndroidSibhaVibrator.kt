package com.mohaberabi.sibha.core.data.vibration

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.core.content.getSystemService
import com.mohaberabi.sibha.core.domain.vibration.SibhaVibrator


class AndroidSibhaVibrator(
    private val context: Context,
) : SibhaVibrator {
    private val vibrator = context.getSystemService<Vibrator>()!!
    override fun vibrate(millisSeconds: Long) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val effect =
                VibrationEffect.createOneShot(millisSeconds, VibrationEffect.DEFAULT_AMPLITUDE)
            vibrator.vibrate(effect)
        } else {
            vibrator.vibrate(millisSeconds)
        }
    }


}