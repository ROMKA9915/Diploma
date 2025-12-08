package com.app.diploma.presentation.screens.main

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.diploma.data.dto.Currency
import com.app.diploma.domain.CryptoRepository
import com.app.diploma.presentation.navigation.Navigator
import com.app.diploma.presentation.screens.detail.DetailScreen
import com.app.diploma.presentation.screens.settings.SettingsScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val navigator: Navigator,
    private val cryptoRepository: CryptoRepository,
) : ViewModel() {

    var isRefreshing by mutableStateOf(false)
        private set

    private val _currenciesState = MutableStateFlow<Result<List<Currency>>?>(null)
    val currenciesState = _currenciesState.asStateFlow()

    init {
        viewModelScope.launch {
            _currenciesState.value = cryptoRepository.getAllCurrencies()
        }
    }

    fun refresh() {
        viewModelScope.launch {
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
