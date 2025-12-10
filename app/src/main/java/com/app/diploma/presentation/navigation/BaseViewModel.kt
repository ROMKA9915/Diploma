package com.app.diploma.presentation.navigation

import kotlinx.coroutines.*

abstract class BaseViewModel {

    protected val scope = CoroutineScope(Dispatchers.Main.immediate)

    fun clear() {
        scope.cancel("view model is cleared")
    }
}
