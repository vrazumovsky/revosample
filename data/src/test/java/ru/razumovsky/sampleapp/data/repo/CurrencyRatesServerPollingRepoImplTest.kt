package ru.razumovsky.sampleapp.data.repo

import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import ru.razumovsky.sampleapp.data.entity.Currency
import ru.razumovsky.sampleapp.data.mapper.CurrencyRatesDtoToEntityMapper
import ru.razumovsky.sampleapp.data.mapper.CurrencyRatesDtoToEntityMapperImpl
import ru.razumovsky.sampleapp.data.network.dto.CurrencyRatesResponse
import ru.razumovsky.sampleapp.data.network.request.CurrencyRatesRequest
import ru.razumovsky.sampleapp.data.scheduler.SchedulerProvider
import java.util.concurrent.TimeUnit

class CurrencyRatesServerPollingRepoImplTest {
    @Mock
    private lateinit var mockRequest: CurrencyRatesRequest

    private lateinit var mockMapper: CurrencyRatesDtoToEntityMapper

    @Mock
    private lateinit var mockSchedulerProvider: SchedulerProvider

    private lateinit var mockResponse: CurrencyRatesResponse
    private val mockDate = "03-03-2019"
    private lateinit var testScheduler: TestScheduler

    private lateinit var repo: CurrencyRatesRepo

    private val mockRates = mapOf(
        Pair(Currency.GBP.value, 1.4f),
        Pair(Currency.AUD.value, 1.3f),
        Pair(Currency.USD.value, 1.1f)
    )

    @Before
    fun initialize() {
        MockitoAnnotations.initMocks(this)
        mockResponse = CurrencyRatesResponse(Currency.EUR.value, mockDate, mockRates)
        mockMapper = CurrencyRatesDtoToEntityMapperImpl()

        whenever(mockRequest.run()).thenReturn(Observable.just(mockResponse))
        repo = CurrencyRatesServerPollingRepoImpl(mockRequest, mockMapper, mockSchedulerProvider)

        testScheduler = TestScheduler()
        RxJavaPlugins.setComputationSchedulerHandler { testScheduler }
        whenever(mockSchedulerProvider.new()).thenReturn(testScheduler)
    }


    @Test
    fun `repo getRates() called and subscribed to Observable, should start polling backend for each second`() {
        val testObserver = repo.getRates()
            .observeOn(testScheduler)
            .test()

        testObserver.assertNotTerminated()
            .assertNoErrors()
            .assertValueCount(0)

        for (i in 1..10) {
            testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)
            testObserver.assertValueCount(i)
            verify(mockRequest, times(i)).run()
        }

        testScheduler.advanceTimeBy(90, TimeUnit.SECONDS)
        testObserver.assertValueCount(100)

        testObserver.dispose()
    }

    @Test
    fun `repo getRatesSingle() should return empty data before polling`() {
        val testSingleObserver = repo.getRatesSingle()
            .observeOn(testScheduler)
            .test()

        testScheduler.advanceTimeBy(1, TimeUnit.MILLISECONDS)

        testSingleObserver
            .assertNoErrors()
            .assertComplete()
            .assertValue { it.isEmpty() }

        testSingleObserver.dispose()
    }

    @Test
    fun `repo getRates() polling, should update cache`() {
        val testObserver = repo.getRates()
            .observeOn(testScheduler)
            .test()

        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)


        val testSingleObserver = repo.getRatesSingle()
            .observeOn(testScheduler)
            .test()

        testScheduler.advanceTimeBy(1, TimeUnit.MILLISECONDS)

        testSingleObserver
            .assertNoErrors()
            .assertComplete()
            .assertValue { it.isNotEmpty() }

        testObserver.dispose()
        testSingleObserver.dispose()
    }
}