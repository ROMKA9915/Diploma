package com.app.diploma.presentation.navigation

import kotlinx.coroutines.flow.StateFlow

interface Navigator {

    val lastScreenFlow: StateFlow<Screen>

    fun navigate(screen: Screen)

    fun pop(onPopLastScreen: () -> Unit = {})

}
