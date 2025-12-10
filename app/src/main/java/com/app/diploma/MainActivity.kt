package com.app.diploma

import android.os.Bundle
import androidx.activity.*
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.diploma.data.local.AppSettings
import com.app.diploma.presentation.screens.main.MainScreen
import com.app.diploma.presentation.theme.DiplomaTheme
import com.app.diploma.presentation.theme.LocalColors
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var appSettings: AppSettings

    private val viewModel by viewModels<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val mainScreen = MainScreen()
        viewModel.navigator.replace(mainScreen)

        setContent {
            val currentTheme by appSettings.currentTheme.collectAsStateWithLifecycle()
            val currentLocale by appSettings.currentLocale.collectAsStateWithLifecycle()
            val currentScreen by viewModel.navigator.lastScreenFlow.collectAsStateWithLifecycle()

            BackHandler {
                viewModel.navigator.pop {
                    finish()
                }
            }

            DiplomaTheme(
                scheme = currentTheme,
                locale = currentLocale,
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
