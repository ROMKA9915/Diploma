package com.app.diploma.data.network

import android.content.SharedPreferences
import com.app.diploma.domain.CryptoRepository
import com.app.diploma.presentation.screens.settings.SettingsViewModel.Companion.CURRENCY_IDS_KEY
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CryptoRepositoryImpl @Inject constructor(
    private val prefs: SharedPreferences,
    private val cryptoService: CryptoService,
) : CryptoRepository {

    override suspend fun getAllCurrencies() = runCatching {
        val filteredIds = prefs.getString(CURRENCY_IDS_KEY, "") ?: ""
        cryptoService.getCurrencies(filteredIds).data
    }
}
