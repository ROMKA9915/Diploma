package com.app.diploma.presentation.screens.detail

import androidx.compose.runtime.*
import com.app.diploma.presentation.navigation.BaseViewModel
import com.app.diploma.presentation.navigation.Navigator
import com.app.diploma.presentation.screens.settings.SettingsScreen
import javax.inject.Inject

class DetailScreenViewModel @Inject constructor(
    private val navigator: Navigator,
) : BaseViewModel() {

    var currentQuantity by mutableIntStateOf(0)
        private set

    fun onIncreaseQuantity() {
        currentQuantity++
    }

    fun onDecreaseQuantity() {
        currentQuantity--
    }

    fun onBackPressed() {
        navigator.pop()
    }

    fun onSettingsClick() {
        val settingsScreen = SettingsScreen()
        navigator.navigate(settingsScreen)
    }
}
