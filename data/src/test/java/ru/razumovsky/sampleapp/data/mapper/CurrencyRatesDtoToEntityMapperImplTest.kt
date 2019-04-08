package ru.razumovsky.sampleapp.data.mapper

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import ru.razumovsky.sampleapp.data.entity.Currency
import ru.razumovsky.sampleapp.data.network.dto.CurrencyRatesResponse

class CurrencyRatesDtoToEntityMapperImplTest {

    private lateinit var mapper: CurrencyRatesDtoToEntityMapperImpl

    private val mockDate = "03-03-2019"

    private lateinit var mockResponse: CurrencyRatesResponse
    private lateinit var emptyMockResponse: CurrencyRatesResponse


    private val mockRates = mapOf(
        Pair(Currency.EUR.value, 1.4f),
        Pair(Currency.AUD.value, 1.3f),
        Pair(Currency.USD.value, 1.1f)
    )


    @Before
    fun initialize() {
        mockResponse = CurrencyRatesResponse(Currency.EUR.value, mockDate, mockRates)
        emptyMockResponse = CurrencyRatesResponse(null, null, null)

        mapper = CurrencyRatesDtoToEntityMapperImpl()
    }


    @Test
    fun `map not null values`() {
        val entity = mapper.map(mockResponse)
        Assert.assertEquals(mockResponse.base, entity.base)
        Assert.assertEquals(mockResponse.date, entity.date)
        mockResponse.rates!!.forEach {
            Assert.assertEquals(it.value, entity.rates[it.key])
        }
    }

    @Test
    fun `map null values to default`() {
        val entity = mapper.map(emptyMockResponse)
        Assert.assertEquals("", entity.base)
        Assert.assertEquals("", entity.date)
        Assert.assertEquals(0, entity.rates.size)
    }

}