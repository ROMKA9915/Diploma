package com.app.diploma.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color

@Composable
fun DiplomaTheme(scheme: ThemeScheme, content: @Composable () -> Unit) {
    val isDarkTheme = when (scheme) {
        ThemeScheme.LIGHT -> false
        ThemeScheme.DARK -> true
        ThemeScheme.DEFAULT -> isSystemInDarkTheme()
    }

    val colors = if (isDarkTheme) DiplomaColorsDark else DiplomaColorsLight

    CompositionLocalProvider(
        LocalColors provides colors,

        content = content,
    )
}

val LocalColors = staticCompositionLocalOf<DiplomaColors> { error("No colors provided") }

enum class ThemeScheme {

    LIGHT,
    DARK,
    DEFAULT,

    ;

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

    override val primary = Color(0xFF120C18)
    override val onPrimary = Color(0xFFF3F2F8)
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
