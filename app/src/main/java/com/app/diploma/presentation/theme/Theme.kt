package com.app.diploma.presentation.theme

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.res.stringResource
import com.app.diploma.R
import com.app.diploma.data.local.Locale
import java.util.Locale.forLanguageTag

@Composable
fun DiplomaTheme(
    scheme: ThemeScheme,
    locale: Locale,
    content: @Composable () -> Unit,
) {
    val isDarkTheme = when (scheme) {
        ThemeScheme.LIGHT -> false
        ThemeScheme.DARK -> true
        ThemeScheme.DEFAULT -> isSystemInDarkTheme()
    }

    val newContext = LocalContext.current.withLocale(locale)
    val colors = if (isDarkTheme) DiplomaColorsDark else DiplomaColorsLight

    CompositionLocalProvider(
        LocalLocale provides locale,
        LocalColors provides colors,
        LocalContext provides newContext,
        LocalResources provides newContext.resources,
        content = content,
    )
}

private fun Context.withLocale(locale: Locale): Context {
    val config = resources.configuration
    config.setLocale(forLanguageTag(locale.tag))
    return createConfigurationContext(config)
}

val LocalColors = staticCompositionLocalOf<DiplomaColors> { error("No colors provided") }
val LocalLocale = staticCompositionLocalOf<Locale> { error("No colors provided") }

enum class ThemeScheme(
    @StringRes private val titleRes: Int,
) {

    LIGHT(R.string.light),
    DARK(R.string.dark),
    DEFAULT(R.string.system),

    ;

    val title @Composable get() = stringResource(titleRes)

    companion object {

        fun retrieveByName(name: String?) = entries.first { it.name == name }
    }
}

interface DiplomaColors {

    val primary: Color
    val onPrimary: Color

    val accent: Color
    val onAccent: Color

    val background: Color
    val onBackground: Color
}

data object DiplomaColorsLight : DiplomaColors {

    override val primary = Color(0xFFF3F2F8)
    override val onPrimary = Color(0xFF120C18)
    override val accent = Color(0xFFA082F1)
    override val onAccent = Color(0xFF120C18)
    override val background = Color(0xFFEDEAFA)
    override val onBackground = Color(0xFF120C18)
}

data object DiplomaColorsDark : DiplomaColors {

    override val primary = Color(0xFFF3F2F8)
    override val onPrimary = Color(0xFF120C18)
    override val accent = Color(0xFFA082F1)
    override val onAccent = Color(0xFF120C18)
    override val background = Color(0xFF120C18)
    override val onBackground = Color(0xFFF3F2F8)
}
