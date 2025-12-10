package com.app.diploma.presentation.screens.detail

import androidx.compose.runtime.*
import com.app.diploma.data.dto.Currency
import com.app.diploma.domain.CryptoRepository
import com.app.diploma.presentation.navigation.BaseViewModel
import com.app.diploma.presentation.navigation.Navigator
import com.app.diploma.presentation.screens.settings.SettingsScreen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailScreenViewModel @Inject constructor(
    private val navigator: Navigator,
    private val cryptoRepository: CryptoRepository,
) : BaseViewModel() {

    var isRefreshing by mutableStateOf(false)
        private set

    private val _currencyUsdRate = MutableStateFlow<Result<Double>?>(null)
    val currencyUsdRate = _currencyUsdRate.asStateFlow()

    var currentQuantity by mutableIntStateOf(0)

    fun requestRate(currency: Currency) {
        scope.launch {
            _currencyUsdRate.value = cryptoRepository.getRateByCurrency(currency)
            isRefreshing = false
        }
    }

    fun onIncreaseQuantity() {
        currentQuantity++
    }

    fun onDecreaseQuantity() {
        currentQuantity--
    }

    fun onBackPressed() {
        navigator.pop()
    }

    fun refresh(currency: Currency) {
        isRefreshing = true
        requestRate(currency)
    }

    fun onSettingsClick() {
        val settingsScreen = SettingsScreen()
        navigator.navigate(settingsScreen)
    }
}
