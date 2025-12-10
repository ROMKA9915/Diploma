package com.app.diploma.data.local

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.app.diploma.presentation.theme.ThemeScheme
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppSettingsImpl @Inject constructor(
    private val prefs: SharedPreferences,
) : AppSettings {

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

        runBlocking {
            changeLocale(locale)
        }

        MutableStateFlow(locale)
    }
    override val currentLocale: StateFlow<Locale> = _currentLocale.asStateFlow()

    override suspend fun setTheme(theme: ThemeScheme) {
        _currentTheme.value = theme
    }

    override suspend fun setLocale(locale: Locale) {
        changeLocale(locale)
        _currentLocale.value = locale
    }

    private suspend fun changeLocale(locale: Locale) {
        withContext(Dispatchers.Main.immediate) {
            val localeList = LocaleListCompat.forLanguageTags(locale.tag)
            AppCompatDelegate.setApplicationLocales(localeList)
        }
    }

    companion object {

        private const val THEME_SCHEME_KEY = "theme_scheme_key"
        private const val LOCALE_KEY = "locale_key"
    }
}
