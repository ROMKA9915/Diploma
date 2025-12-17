package com.app.diploma.presentation.screens.main

import android.content.SharedPreferences
import androidx.compose.runtime.*
import androidx.core.content.edit
import com.app.diploma.data.dto.Currency
import com.app.diploma.domain.CryptoRepository
import com.app.diploma.presentation.navigation.BaseViewModel
import com.app.diploma.presentation.navigation.Navigator
import com.app.diploma.presentation.screens.detail.DetailScreen
import com.app.diploma.presentation.screens.settings.SettingsScreen
import com.app.diploma.presentation.screens.settings.SettingsViewModel.Companion.CURRENCY_IDS_KEY
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class MainScreenViewModel @Inject constructor(
    private val prefs: SharedPreferences,
    private val navigator: Navigator,
    private val cryptoRepository: CryptoRepository,
) : BaseViewModel() {

    var isRefreshing by mutableStateOf(false)
        private set

    private val _currenciesState = MutableStateFlow<Result<List<Currency>>?>(null)
    val currenciesState = _currenciesState.asStateFlow()

    init {
        scope.launch {
            while (isActive) {
                requestRates()
                delay(5000)
            }
        }
    }

    private suspend fun requestRates() {
        val currencies = cryptoRepository.getAllCurrencies()
        currencies.onSuccess {
            // записываем все валюты только при первом запуске
            val oldValue = prefs.getString(CURRENCY_IDS_KEY, "") ?: ""
            val contains = prefs.contains(CURRENCY_IDS_KEY)

            if (contains.not() || oldValue.isEmpty()) {
                prefs.edit(true) {
                    putString(CURRENCY_IDS_KEY, it.map { it.id }.joinToString(","))
                }
            }
        }
        _currenciesState.value = currencies
    }

    fun refresh() {
        scope.launch {
            isRefreshing = true
            _currenciesState.value = null
            _currenciesState.value = cryptoRepository.getAllCurrencies()
            isRefreshing = false
        }
    }

    fun onSettingsClick() {
        val settingsScreen = SettingsScreen()
        navigator.navigate(settingsScreen)
    }

    fun onCurrencyClick(currency: Currency) {
        val detailScreen = DetailScreen(currency)
        navigator.navigate(detailScreen)
    }
}
