package com.app.diploma

import android.content.SharedPreferences
import com.app.diploma.data.dto.Currency
import com.app.diploma.data.network.*
import com.app.diploma.presentation.screens.settings.SettingsViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class ExampleUnitTest {

    @Test
    fun `currency repository returns returns correct list`() {
        val correctResponse = ApiResponse(
            data = listOf(
                Currency(
                    id = "test_1",
                    symbol = "t_1",
                    currencySymbol = "TEST_1",
                    rateUsd = "1",
                ),
                Currency(
                    id = "test_2",
                    symbol = "t_2",
                    currencySymbol = "TEST_2",
                    rateUsd = "2",
                ),
                Currency(
                    id = "test_3",
                    symbol = "t_3",
                    currencySymbol = "TEST_3",
                    rateUsd = "3",
                ),
            )
        )
        val mockService = mock<CryptoService> {
            onBlocking { getCurrencies("") } doReturn correctResponse
        }
        val mockPrefs = mock<SharedPreferences> {
            on { getString(SettingsViewModel.CURRENCY_IDS_KEY, "") } doReturn ""
        }
        val repo = CryptoRepositoryImpl(mockPrefs, mockService)

        val actualResponse = runBlocking {
            repo.getAllCurrencies()
        }

        assert(actualResponse.isSuccess && actualResponse.getOrThrow() == correctResponse.data)
    }
}
