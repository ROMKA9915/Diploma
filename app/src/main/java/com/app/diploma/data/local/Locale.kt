package com.app.diploma.data.local

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.app.diploma.R

enum class Locale(
    val tag: String,
    @StringRes private val titleRes: Int,
) {

    Ru("ru", R.string.russian),
    En("en", R.string.english),

    ;

    val title @Composable get() = stringResource(titleRes)

    companion object {

        fun retrieveByName(name: String?) = entries.find { it.name == name }
        fun retrieveByLanguageTag(tag: String?) = entries.find { it.tag == tag }
    }

}
