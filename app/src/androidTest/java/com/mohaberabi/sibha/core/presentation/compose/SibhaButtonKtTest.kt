package com.mohaberabi.sibha.core.presentation.compose

import SibhaButton
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.mohaberabi.sibha.core.presentation.theme.SibhaTheme
import com.mohaberabi.sibha.sibha_app.MainActivity
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class SibhaButtonTest {


    @get:Rule
    val composeRule = createComposeRule()
//    val androidComposeRule = createAndroidComposeRule<MainActivity>()


    @Test
    fun testSibhaButton() {
        composeRule.setContent {
            SibhaTheme {
                SibhaButton(
                    label = "Loser"
                )

            }
        }
        composeRule.onNodeWithText("Loser").assertExists()
        composeRule.onNodeWithText("Loser").assertIsDisplayed()

    }
}