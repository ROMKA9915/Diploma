package com.app.diploma.presentation.navigation

import com.app.diploma.presentation.screens.main.MainScreen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class NavigatorImpl @Inject constructor() : Navigator {

    private val backstack = mutableListOf<Screen>()

    private val _lastScreenFlow = run {
        val initialScreen = MainScreen()
        backstack.add(initialScreen)

        MutableStateFlow<Screen>(initialScreen)
    }
    override val lastScreenFlow = _lastScreenFlow.asStateFlow()

    override fun navigate(screen: Screen) {
        _lastScreenFlow.value = screen
        backstack.add(screen)
    }

    override fun pop(onPopLastScreen: () -> Unit) {
        if (backstack.size > 1) {
            backstack.removeLast()
            _lastScreenFlow.value = backstack.last()
        } else {
            onPopLastScreen()
        }
    }
}
