package com.app.diploma

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.diploma.data.local.AppSettings
import com.app.diploma.presentation.navigation.Navigator
import com.app.diploma.presentation.theme.DiplomaTheme
import com.app.diploma.presentation.theme.LocalColors
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var prefs: SharedPreferences
    @Inject lateinit var appSettings: AppSettings
    @Inject lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        prefs.registerOnSharedPreferenceChangeListener(appSettings)

        setContent {
            val currentTheme by appSettings.currentTheme.collectAsStateWithLifecycle()
            val currentScreen by navigator.lastScreenFlow.collectAsStateWithLifecycle()

            BackHandler {
                navigator.pop {
                    finish()
                }
            }

            DiplomaTheme(
                scheme = currentTheme,
            ) {
                val colors = LocalColors.current

                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(colors.background)
                        .systemBarsPadding(),
                    containerColor = colors.background,
                    topBar = currentScreen::TopBar,
                ) { paddingValues ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        contentAlignment = Alignment.Center,
                    ) {
                        currentScreen.Content()
                    }
                }
            }
        }
    }
}
