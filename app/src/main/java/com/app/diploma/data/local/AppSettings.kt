package com.app.diploma.data.local

import android.content.SharedPreferences
import com.app.diploma.presentation.theme.ThemeScheme
import kotlinx.coroutines.flow.StateFlow

interface AppSettings : SharedPreferences.OnSharedPreferenceChangeListener {

    val currentTheme: StateFlow<ThemeScheme>
    val currentLocale: StateFlow<Locale>

    fun setTheme(theme: ThemeScheme)
    fun setLocale(locale: Locale)
}
