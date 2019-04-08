package ru.razumovsky.sampleapp.data.repo

import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import ru.razumovsky.sampleapp.data.network.request.CurrencyRatesRequest

class CurrencyRatesRepoImplTest {

    @Mock
    private lateinit var mockRequest: CurrencyRatesRequest

    private lateinit var repo: CurrencyRatesRepo


    @Before
    fun initialize() {
        MockitoAnnotations.initMocks(this)

        repo = CurrencyRatesRepoImpl(mockRequest)
    }


    @Test
    fun `repo getRates called, should run request`() {
        repo.getRates()
        verify(mockRequest).run()
    }
}