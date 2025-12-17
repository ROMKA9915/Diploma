package com.app.diploma.presentation.screens.settings

import android.content.SharedPreferences
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateSet
import androidx.core.content.edit
import com.app.diploma.data.dto.Profile
import com.app.diploma.data.local.AppSettings
import com.app.diploma.data.local.Locale
import com.app.diploma.domain.CryptoRepository
import com.app.diploma.presentation.navigation.BaseViewModel
import com.app.diploma.presentation.navigation.Navigator
import com.app.diploma.presentation.theme.ThemeScheme
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val prefs: SharedPreferences,
    private val navigator: Navigator,
    private val appSettings: AppSettings,
    private val cryptoRepository: CryptoRepository,
) : BaseViewModel() {

    val themeScheme = appSettings.currentTheme
    val locale = appSettings.currentLocale

    val allCurrencyIds = (prefs.getString(CURRENCY_IDS_KEY, "") ?: "").split(",")

    val selectedCurrencyIds = SnapshotStateSet<String>().also {
        it.addAll(allCurrencyIds)
    }

    var profileData by mutableStateOf<Profile?>(null)

    init {
        scope.launch {
            profileData = cryptoRepository.getProfileData().getOrNull()
        }
    }

    fun onBackPressed() {
        navigator.pop()
    }

    fun onCurrencyIdClick(currencyId: String) {
        val isAdded = selectedCurrencyIds.add(currencyId)

        prefs.edit(true) {
            val oldValue = prefs.getString(CURRENCY_IDS_KEY, "") ?: ""
            val oldValueFinal = oldValue.split(",").toMutableSet()
            if (isAdded) {
                oldValueFinal.add(currencyId)
            } else {
                oldValueFinal.remove(currencyId)
                selectedCurrencyIds.remove(currencyId)
            }
            putString(CURRENCY_IDS_KEY, oldValueFinal.joinToString(","))
        }
    }

    fun onThemeSelected(themeScheme: ThemeScheme) {
        scope.launch {
            appSettings.setTheme(themeScheme)
        }
    }

    fun onLocaleSelected(locale: Locale) {
        scope.launch {
            appSettings.setLocale(locale)
        }
    }

    companion object {

        const val CURRENCY_IDS_KEY = "currency_ids_key"
    }
}
