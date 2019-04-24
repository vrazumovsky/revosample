package ru.razumovsky.sampleapp.screens.main.currencychange

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import ru.razumovsky.sampleapp.domain.usecase.GetCurrencyRatesUseCase

class CurrencyChangePresenterTest {
    @Mock
    private lateinit var mockView: CurrencyChangeView

    @Mock
    private lateinit var mockUIMapper: CurrencyRateUIMapper

    @Mock
    private lateinit var mockUseCase: GetCurrencyRatesUseCase


    private lateinit var presenter: CurrencyChangePresenter

    @Before
    fun initialize() {
        MockitoAnnotations.initMocks(this)

        presenter = CurrencyChangePresenterImpl(
            view = mockView,
            mapper = mockUIMapper,
            useCase = mockUseCase)
    }

}