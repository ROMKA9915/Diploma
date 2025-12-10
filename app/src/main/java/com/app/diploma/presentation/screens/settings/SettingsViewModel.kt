package com.app.diploma.presentation.screens.settings

import com.app.diploma.data.local.AppSettings
import com.app.diploma.data.local.Locale
import com.app.diploma.presentation.navigation.BaseViewModel
import com.app.diploma.presentation.navigation.Navigator
import com.app.diploma.presentation.theme.ThemeScheme
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val navigator: Navigator,
    private val appSettings: AppSettings,
) : BaseViewModel() {

    val themeScheme = appSettings.currentTheme
    val locale = appSettings.currentLocale

    fun onBackPressed() {
        navigator.pop()
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
}
