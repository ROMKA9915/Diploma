package com.app.diploma.presentation.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.diploma.App
import com.app.diploma.R
import com.app.diploma.presentation.navigation.Screen
import com.app.diploma.presentation.theme.LocalColors
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent

class MainScreen() : Screen() {

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface MainScreenEntryPoint {

        fun mainViewModel(): MainScreenViewModel
    }

    override val viewModel = EntryPointAccessors.fromApplication(
        App.instance,
        MainScreenEntryPoint::class.java,
    ).mainViewModel()

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
            Spacer(modifier = Modifier.size(40.dp))
            Text(
                text = stringResource(R.string.currencies),
                color = colors.onBackground,
                fontSize = 26.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold,
            )
            Icon(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .clickable(onClick = viewModel::onSettingsClick),
                imageVector = ImageVector.vectorResource(R.drawable.settings),
                contentDescription = null,
                tint = colors.onBackground,
            )
        }
    }

    @Composable
    override fun Content() {
        val colors = LocalColors.current

        val currenciesState = viewModel.currenciesState.collectAsStateWithLifecycle().value

        val state = rememberPullToRefreshState()

        PullToRefreshBox(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp),
            isRefreshing = viewModel.isRefreshing,
            state = state,
            onRefresh = viewModel::refresh,
            contentAlignment = Alignment.TopCenter,
            indicator = {
                Indicator(
                    modifier = Modifier.align(Alignment.TopCenter),
                    isRefreshing = viewModel.isRefreshing,
                    state = state,
                    containerColor = colors.accent,
                    color = colors.onAccent,
                )
            },
        ) {
            when {
                currenciesState == null -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth(0.3f)
                            .aspectRatio(1f),
                        color = colors.accent,
                        trackColor = Color.Transparent,
                        strokeWidth = 16.dp,
                        strokeCap = StrokeCap.Round,
                    )
                }

                currenciesState.isFailure -> {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(R.string.fetching_error),
                        color = colors.onBackground,
                        fontSize = 28.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold,
                    )
                }

                currenciesState.isSuccess -> {
                    val currencies = currenciesState.getOrThrow()

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 20.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp),
                        horizontalArrangement = Arrangement.spacedBy(20.dp),
                    ) {
                        items(
                            items = currencies,
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(1f)
                                    .clip(RoundedCornerShape(16.dp))
                                    .clickable {
                                        viewModel.onCurrencyClick(it)
                                    }
                                    .background(colors.accent)
                                    .padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(12.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Text(
                                        modifier = Modifier.weight(1f),
                                        text = it.symbol,
                                        color = colors.onAccent,
                                        fontSize = 22.sp,
                                        fontWeight = FontWeight.Medium,
                                    )
                                    it.currencySymbol?.let { currencySymbol ->
                                        Text(
                                            text = currencySymbol,
                                            color = colors.onAccent,
                                            fontSize = 26.sp,
                                            textAlign = TextAlign.Center,
                                            fontWeight = FontWeight.SemiBold,
                                        )
                                    }
                                }
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = it.id,
                                    color = colors.onAccent,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Medium,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
