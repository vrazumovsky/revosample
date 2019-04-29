package ru.razumovsky.sampleapp.screens.main.currencychange

import com.nhaarman.mockitokotlin2.*
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
import java.util.concurrent.TimeUnit

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

    private val testScheduler = TestScheduler()


    @Before
    fun initialize() {
        MockitoAnnotations.initMocks(this)
        whenever(mockUseCase.executePolling()).thenReturn(Observable.just(mockRates))
        whenever(mockView.getCurrencies()).thenReturn(emptyList())

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

    @Test
    fun `polling emits item, should show currencies on view`() {
        presenter.onStart()

        testScheduler.advanceTimeBy(1, TimeUnit.MILLISECONDS)
        verify(mockView).showCurrencies(any())
    }

    @Test
    fun `polling emits item, should NOT show currencies on view if it is scrolling`() {
        whenever(mockView.isScrolling()).thenReturn(true)
        presenter.onStart()

        testScheduler.advanceTimeBy(1, TimeUnit.MILLISECONDS)
        verify(mockView, times(0)).showCurrencies(any())
    }

    @Test
    fun `polling emits item, should show empty message if response is empty`() {
        whenever(mockUseCase.executePolling()).thenReturn(Observable.just(emptyList()))
        presenter.onStart()

        testScheduler.advanceTimeBy(1, TimeUnit.MILLISECONDS)
        verify(mockView).showEmptyMessage()
    }

    @Test
    fun `polling emits item, should map values to items`() {
        presenter.onStart()
        verify(mockUIMapper).map(any())
    }

}