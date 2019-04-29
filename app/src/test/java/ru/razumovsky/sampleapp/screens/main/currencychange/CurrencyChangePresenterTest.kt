package ru.razumovsky.sampleapp.screens.main.currencychange

import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import ru.razumovsky.sampleapp.data.entity.Currency
import ru.razumovsky.sampleapp.data.scheduler.SchedulerProvider
import ru.razumovsky.sampleapp.domain.usecase.GetCurrencyRatesUseCase
import ru.razumovsky.sampleapp.domain.viewmodel.CurrencyRateViewModel

class CurrencyChangePresenterTest {
    @Mock
    private lateinit var mockView: CurrencyChangeView

    @Mock
    private lateinit var mockUIMapper: CurrencyRateUIMapper

    @Mock
    private lateinit var mockUseCase: GetCurrencyRatesUseCase

    @Mock
    private lateinit var mockSchedulerProvider: SchedulerProvider


    private lateinit var presenter: CurrencyChangePresenter

    private val mockRates = listOf(
        CurrencyRateViewModel(Currency.EUR.value, 1.0f),
        CurrencyRateViewModel(Currency.USD.value, 1.1f),
        CurrencyRateViewModel(Currency.AUD.value, 0.9f),
        CurrencyRateViewModel(Currency.GBP.value, 1.4f)
    )

    @Before
    fun initialize() {
        MockitoAnnotations.initMocks(this)
        whenever(mockUseCase.executePolling()).thenReturn(Observable.just(mockRates))
        whenever(mockView.getCurrencies()).thenReturn(emptyList())

        val testScheduler = TestScheduler()
        whenever(mockSchedulerProvider.main()).thenReturn(testScheduler)

        presenter = CurrencyChangePresenterImpl(
            view = mockView,
            mapper = mockUIMapper,
            useCase = mockUseCase,
            schedulerProvider = mockSchedulerProvider)
    }


    @Test
    fun `presenter onStart() called, should start polling`() {
        presenter.onStart()
        verify(mockUseCase).executePolling()
    }

    @Test
    fun `polling should be observed on main thread`() {
        presenter.onStart()
        verify(mockSchedulerProvider).main()
    }

}