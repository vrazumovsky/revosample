package ru.razumovsky.sampleapp.domain.usecase

import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import ru.razumovsky.sampleapp.data.entity.Currency
import ru.razumovsky.sampleapp.data.repo.CurrencyRatesRepo

class GetCurrencyRatesUseCaseImplTest {

    @Mock
    private lateinit var mockRepo: CurrencyRatesRepo

    private lateinit var testScheduler: TestScheduler

    private lateinit var useCase: GetCurrencyRatesUseCase

    private val mockRates = mapOf(
        Pair(Currency.GBP.value, 1.4f),
        Pair(Currency.AUD.value, 1.3f),
        Pair(Currency.EUR.value, 1.0f),
        Pair(Currency.USD.value, 1.1f)
    )

    @Before
    fun initialize() {
        MockitoAnnotations.initMocks(this)

        testScheduler = TestScheduler()
        RxJavaPlugins.setComputationSchedulerHandler { testScheduler }

        whenever(mockRepo.getRates()).thenReturn(Observable.just(mockRates))
        whenever(mockRepo.getRatesSingle()).thenReturn(Single.just(mockRates))

        useCase = GetCurrencyRatesUseCaseImpl(mockRepo)
    }


    @Test
    fun `usecase executePolling() called,should call repo getRates()`() {
        useCase.executePolling()
        verify(mockRepo).getRates()
    }

    @Test
    fun `usecase execute() called,should call repo getRatesSingle()`() {
        useCase.execute()
        verify(mockRepo).getRatesSingle()
    }

    @Test
    fun `usecase executePolling() item emitted, should map rates`() {
        useCase.executePolling().test()
            .assertValue { it.size == mockRates.size }
            .assertValue {
                it.filter { mockRates[it.name] != null }.size == mockRates.size
            }
    }

    @Test
    fun `usecase execute(), should map rates`() {
        useCase.execute().test()
            .assertValue { it.size == mockRates.size }
            .assertValue {
                it.filter { mockRates[it.name] != null }.size == mockRates.size
            }
    }

    @Test
    fun `usecase executePolling(), should make Euro first in the list`() {
        useCase.executePolling().test()
            .assertValue { it.first().name == Currency.EUR.value }
    }

    @Test
    fun `usecase execute(), should make Euro first in the list`() {
        useCase.execute().test()
            .assertValue { it.first().name == Currency.EUR.value }
    }
}