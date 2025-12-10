package com.app.diploma

import com.app.diploma.data.dto.Currency
import com.app.diploma.data.network.*
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
                ),
                Currency(
                    id = "test_2",
                    symbol = "t_2",
                    currencySymbol = "TEST_2",
                ),
                Currency(
                    id = "test_3",
                    symbol = "t_3",
                    currencySymbol = "TEST_3",
                ),
            )
        )
        val mockService = mock<CryptoService> {
            onBlocking { getCurrencies() } doReturn correctResponse
        }
        val repo = CryptoRepositoryImpl(mockService)

        val actualResponse = runBlocking {
            repo.getAllCurrencies()
        }

        assert(actualResponse.isSuccess && actualResponse.getOrThrow() == correctResponse.data)
    }
}
