package com.app.diploma.presentation.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.app.diploma.data.dto.Currency
import com.app.diploma.presentation.navigation.Screen
import com.app.diploma.presentation.theme.LocalColors
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import kotlin.math.roundToLong

class DetailScreen(
    private val currency: Currency,
) : Screen() {

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface DetailScreenEntryPoint {

        fun detailViewModel(): DetailScreenViewModel
    }

    override val viewModel = EntryPointAccessors.fromApplication(
        App.instance,
        DetailScreenEntryPoint::class.java,
    ).detailViewModel()

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
                text = currency.symbol,
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

        val state = rememberPullToRefreshState()

        LaunchedEffect(Unit) {
            viewModel.requestRate(currency)
        }

        PullToRefreshBox(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp),
            isRefreshing = viewModel.isRefreshing,
            state = state,
            onRefresh = { viewModel.refresh(currency) },
            contentAlignment = Alignment.TopCenter,
            indicator = {
                PullToRefreshDefaults.Indicator(
                    modifier = Modifier.align(Alignment.TopCenter),
                    isRefreshing = viewModel.isRefreshing,
                    state = state,
                    containerColor = colors.accent,
                    color = colors.onAccent,
                )
            },
        ) {
            val currencyRate = viewModel.currencyUsdRate.collectAsStateWithLifecycle().value

            when {
                currencyRate == null -> {
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

                currencyRate.isFailure -> {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(R.string.fetching_error),
                        color = colors.onBackground,
                        fontSize = 28.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold,
                    )
                }

                currencyRate.isSuccess -> {
                    val rate = currencyRate.getOrThrow()

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        val quantity = viewModel.currentQuantity
                        val text = buildString {
                            append(quantity)
                            append(" * ")
                            append(currency.symbol)
                            append(" = ")
                            val finalQuantity = quantity * rate
                            append(finalQuantity.roundToLong())
                            append(" ")
                            append("USD")
                        }
                        Text(
                            text = text,
                            color = colors.onBackground,
                            fontSize = 24.sp,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.SemiBold,
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
                            Icon(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .clickable(
                                        enabled = quantity > 2,
                                        onClick = viewModel::onDecreaseQuantity,
                                    ),
                                imageVector = ImageVector.vectorResource(R.drawable.minus),
                                contentDescription = null,
                                tint = colors.onAccent,
                            )
                            Text(
                                text = currency.symbol,
                                color = colors.onAccent,
                                fontSize = 26.sp,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.SemiBold,
                            )
                            Icon(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .clickable(onClick = viewModel::onIncreaseQuantity),
                                imageVector = ImageVector.vectorResource(R.drawable.plus),
                                contentDescription = null,
                                tint = colors.onAccent,
                            )
                        }
                    }
                }
            }
        }
    }
}
