package com.mohaberabi.sibha.features.home.presentation.screen

import android.Manifest
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import com.mohaberabi.sibha.sibha_app.MainActivity
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import androidx.test.rule.GrantPermissionRule

class HomeScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @get:Rule
    val grantPermissionRule = GrantPermissionRule.grant(Manifest.permission.POST_NOTIFICATIONS)

    @Test
    fun testShowingDrawer() = runTest {
        composeRule.awaitIdle()
        HomeScreenTestRobot(composeRule).clickOnFab()
            .assertResetButtonDisplayed()
            .assertSoundButtonDisplayed()
            .assertVibrationButtonDisplayed()
            .assertVibrationButtonDisplayed()
    }
}