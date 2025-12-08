package com.app.diploma.data.local

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.app.diploma.presentation.theme.ThemeScheme
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppSettingsImpl @Inject constructor(
    private val prefs: SharedPreferences,
) : AppSettings, SharedPreferences.OnSharedPreferenceChangeListener {

    private val _currentTheme = run {
        val theme = ThemeScheme.retrieveByName(
            prefs.getString(
                THEME_SCHEME_KEY,
                ThemeScheme.DEFAULT.name,
            )
        )
        MutableStateFlow(theme)
    }
    override val currentTheme = _currentTheme.asStateFlow()

    private val _currentLocale = run {
        val savedLocale = prefs.getString(LOCALE_KEY, null)
        val locale = if (savedLocale == null) {
            try {
                val languageTag = AppCompatDelegate.getApplicationLocales().get(0)?.toLanguageTag()
                Locale.retrieveByLanguageTag(languageTag)
            } catch (_: Exception) {
                null
            }
        } else {
            Locale.retrieveByName(savedLocale)
        } ?: Locale.Ru

        MutableStateFlow(locale)
    }
    override val currentLocale: StateFlow<Locale> = _currentLocale.asStateFlow()

    override fun setTheme(theme: ThemeScheme) {
        _currentTheme.value = theme
    }

    override fun setLocale(locale: Locale) {
        val localeList = LocaleListCompat.forLanguageTags(locale.tag)
        AppCompatDelegate.setApplicationLocales(localeList)
        _currentLocale.value = locale
    }

    override fun onSharedPreferenceChanged(
        sharedPreferences: SharedPreferences?,
        key: String?,
    ) {
        if (key == THEME_SCHEME_KEY) {
            val newValue = sharedPreferences?.getString(THEME_SCHEME_KEY, ThemeScheme.DEFAULT.name)
                ?: ThemeScheme.DEFAULT.name
            _currentTheme.value = ThemeScheme.retrieveByName(newValue)
        }
    }

    companion object {

        private const val THEME_SCHEME_KEY = "theme_scheme_key"
        private const val LOCALE_KEY = "locale_key"
    }
}
