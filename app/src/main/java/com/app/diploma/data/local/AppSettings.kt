package com.app.diploma.data.local

import com.app.diploma.presentation.theme.ThemeScheme
import kotlinx.coroutines.flow.StateFlow

interface AppSettings {

    val currentTheme: StateFlow<ThemeScheme>
    val currentLocale: StateFlow<Locale>

    suspend fun setTheme(theme: ThemeScheme)
    suspend fun setLocale(locale: Locale)
}
