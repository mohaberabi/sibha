package com.mohaberabi.sibha.features.home.presentation.screen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.mohaberabi.sibha.sibha_app.MainActivity
import kotlinx.serialization.Serializable


typealias HomeScreenComposeRule = AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>

class HomeScreenTestRobot(
    private val composeRule: HomeScreenComposeRule
) {

    fun clickOnFab(
        fabContentDesc: String = "clickToOpenSettings",
    ): HomeScreenTestRobot {
        composeRule.onNodeWithContentDescription(fabContentDesc).performClick()
        return this

    }

    fun assertMoreButtonDisplayed(
        text: String = "More",
    ): HomeScreenTestRobot {
        composeRule.onNodeWithText(text).assertIsDisplayed()
        return this
    }


    fun assertVibrationButtonDisplayed(
        text: String = "Vibration",
    ): HomeScreenTestRobot {
        composeRule.onNodeWithText(text).assertIsDisplayed()
        return this
    }

    fun assertSoundButtonDisplayed(
        text: String = "Sound",
    ): HomeScreenTestRobot {
        composeRule.onNodeWithText(text).assertIsDisplayed()
        return this
    }

    fun assertResetButtonDisplayed(
        text: String = "Reset",
    ): HomeScreenTestRobot {
        composeRule.onNodeWithText(text).assertIsDisplayed()
        return this
    }

}