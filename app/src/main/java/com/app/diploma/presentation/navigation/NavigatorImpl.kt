package com.app.diploma.presentation.navigation

import com.app.diploma.presentation.screens.splash.SplashScreen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NavigatorImpl @Inject constructor() : Navigator {

    private val backstack = mutableListOf<Screen>()

    private val _lastScreenFlow = run {
        val initialScreen = SplashScreen()
        backstack.add(initialScreen)

        MutableStateFlow<Screen>(initialScreen)
    }
    override val lastScreenFlow = _lastScreenFlow.asStateFlow()

    override fun navigate(screen: Screen) {
        _lastScreenFlow.value = screen
        backstack.add(screen)
    }

    override fun replace(screen: Screen) {
        _lastScreenFlow.value = screen
        if (backstack.size > 1) {
            backstack.removeAt(backstack.lastIndex)
        } else {
            backstack.clear()
        }
        backstack.add(screen)
    }

    override fun pop(onPopLastScreen: () -> Unit) {
        if (backstack.size > 1) {
            val dropped = backstack.removeAt(backstack.lastIndex)
            dropped.viewModel.clear()
            _lastScreenFlow.value = backstack.last()
        } else {
            onPopLastScreen()
        }
    }
}
