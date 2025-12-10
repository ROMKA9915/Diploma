package com.app.diploma.presentation.screens.settings

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.diploma.App
import com.app.diploma.R
import com.app.diploma.data.local.Locale
import com.app.diploma.presentation.navigation.Screen
import com.app.diploma.presentation.theme.LocalColors
import com.app.diploma.presentation.theme.ThemeScheme
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import kotlin.math.roundToInt

class SettingsScreen : Screen() {

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface SettingsScreenEntryPoint {

        fun settingsViewModel(): SettingsViewModel
    }

    override val viewModel = EntryPointAccessors.fromApplication(
        App.instance,
        SettingsScreenEntryPoint::class.java,
    ).settingsViewModel()

    @Composable
    override fun TopBar() {
        val colors = LocalColors.current

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .clickable(onClick = viewModel::onBackPressed),
                imageVector = ImageVector.vectorResource(R.drawable.chevron_left),
                contentDescription = null,
                tint = colors.onBackground,
            )
            Text(
                text = stringResource(R.string.settings),
                color = colors.onBackground,
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold,
            )
            Spacer(modifier = Modifier)
        }
    }

    @Composable
    override fun Content() {
        val colors = LocalColors.current

        val themeScheme by viewModel.themeScheme.collectAsStateWithLifecycle()
        val locale by viewModel.locale.collectAsStateWithLifecycle()

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = stringResource(R.string.theme),
                    color = colors.onBackground,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(colors.accent)
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    ThemeScheme.entries.forEach {
                        val isSelected = themeScheme == it
                        val weight by animateFloatAsState(
                            targetValue = if (isSelected) 1.25f else 1f,
                        )
                        val textSize by animateDpAsState(
                            targetValue = if (isSelected) 22.dp else 20.dp,
                        )
                        val fontWeight by animateFloatAsState(
                            targetValue = if (isSelected) 700f else 500f,
                        )
                        val modifier = if (isSelected) {
                            Modifier
                                .weight(weight)
                                .clip(RoundedCornerShape(16.dp))
                                .border(
                                    width = 2.dp,
                                    color = colors.primary,
                                    shape = RoundedCornerShape(16.dp),
                                )
                                .padding(horizontal = 4.dp, vertical = 8.dp)
                        } else {
                            Modifier
                                .weight(weight)
                                .clip(RoundedCornerShape(16.dp))
                        }

                        Text(
                            modifier = modifier.clickable {
                                viewModel.onThemeSelected(it)
                            },
                            text = it.title,
                            color = if (isSelected) colors.primary else colors.onAccent,
                            fontSize = with(LocalDensity.current) {
                                textSize.toSp()
                            },
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight(fontWeight.roundToInt()),
                        )
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = stringResource(R.string.locale),
                    color = colors.onBackground,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(colors.accent)
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Locale.entries.forEach {
                        val isSelected = locale == it
                        val weight by animateFloatAsState(
                            targetValue = if (isSelected) 1.25f else 1f,
                        )
                        val textSize by animateDpAsState(
                            targetValue = if (isSelected) 22.dp else 20.dp,
                        )
                        val fontWeight by animateFloatAsState(
                            targetValue = if (isSelected) 700f else 500f,
                        )
                        val modifier = if (isSelected) {
                            Modifier
                                .weight(weight)
                                .clip(RoundedCornerShape(16.dp))
                                .border(
                                    width = 2.dp,
                                    color = colors.primary,
                                    shape = RoundedCornerShape(16.dp),
                                )
                                .padding(horizontal = 4.dp, vertical = 8.dp)
                        } else {
                            Modifier
                                .weight(weight)
                                .clip(RoundedCornerShape(16.dp))
                        }

                        Text(
                            modifier = modifier.clickable {
                                viewModel.onLocaleSelected(it)
                            },
                            text = it.title,
                            color = if (isSelected) colors.primary else colors.onAccent,
                            fontSize = with(LocalDensity.current) {
                                textSize.toSp()
                            },
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight(fontWeight.roundToInt()),
                        )
                    }
                }
            }
        }
    }
}
