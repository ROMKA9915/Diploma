package com.app.diploma.presentation.navigation

import androidx.compose.runtime.Composable

abstract class Screen {

    @Composable
    abstract fun Content()

    @Composable
    open fun TopBar() {
    }
}
