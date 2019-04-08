package ru.razumovsky.sampleapp.data.repo

import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import ru.razumovsky.sampleapp.data.entity.Currency
import ru.razumovsky.sampleapp.data.entity.CurrencyRates
import ru.razumovsky.sampleapp.data.mapper.CurrencyRatesDtoToEntityMapper
import ru.razumovsky.sampleapp.data.network.dto.CurrencyRatesResponse
import ru.razumovsky.sampleapp.data.network.request.CurrencyRatesRequest

class CurrencyRatesRepoImplTest {

    @Mock
    private lateinit var mockRequest: CurrencyRatesRequest

    @Mock
    private lateinit var mockMapper: CurrencyRatesDtoToEntityMapper

    private lateinit var repo: CurrencyRatesRepo

    private lateinit var mockResponse: CurrencyRatesResponse
    private lateinit var mockEntity: CurrencyRates

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
        mockEntity = CurrencyRates(Currency.EUR.value, mockDate, mockRates)
        whenever(mockRequest.run()).thenReturn(Observable.just(mockResponse))
        whenever(mockMapper.map(mockResponse)).thenReturn(mockEntity)

        repo = CurrencyRatesRepoImpl(mockRequest, mockMapper)
    }


    @Test
    fun `repo getRates called, should run request`() {
        repo.getRates()
        verify(mockRequest).run()
    }

    @Test
    fun `repo getRates called, should run mapper after successful request`() {
        repo.getRates().blockingSingle()
        verify(mockMapper).map(mockResponse)
    }

    @Test
    fun `repo getRates called, CurrencyRatesResponse doesn't have base rate in its map, return list size should equals response rates size plus one`() {
        val rates = repo.getRates().blockingSingle()
        assertEquals(mockResponse.rates!!.size + 1, rates.size)

        verify(mockRequest).run()
    }

}