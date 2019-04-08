package ru.razumovsky.sampleapp.data.repo

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import ru.razumovsky.sampleapp.data.entity.Currency
import ru.razumovsky.sampleapp.data.network.dto.CurrencyRatesResponse
import ru.razumovsky.sampleapp.data.network.request.CurrencyRatesRequest

class CurrencyRatesRepoImplTest {

    @Mock
    private lateinit var mockRequest: CurrencyRatesRequest

    private lateinit var repo: CurrencyRatesRepo

    private lateinit var mockResponse: CurrencyRatesResponse

    private val mockDate = "03-03-2019"

    private val mockRates = mapOf(
        Pair(Currency.EUR.value, 1.4f),
        Pair(Currency.AUD.value, 1.3f),
        Pair(Currency.USD.value, 1.1f)
    )


    @Before
    fun initialize() {
        MockitoAnnotations.initMocks(this)

        mockResponse = CurrencyRatesResponse(Currency.EUR.value, mockDate, mockRates)
        whenever(mockRequest.run()).thenReturn(Observable.just(mockResponse))

        repo = CurrencyRatesRepoImpl(mockRequest)
    }


    @Test
    fun `repo getRates called, should run request`() {
        repo.getRates()
        verify(mockRequest).run()
    }
}