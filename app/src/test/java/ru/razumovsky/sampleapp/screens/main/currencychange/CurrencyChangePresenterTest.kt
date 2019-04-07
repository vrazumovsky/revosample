package ru.razumovsky.sampleapp.screens.main.currencychange

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class CurrencyChangePresenterTest {
    @Mock
    private lateinit var mockView: CurrencyChangeView


    private lateinit var presenter: CurrencyChangePresenter

    @Before
    fun initialize() {
        MockitoAnnotations.initMocks(this)

        presenter = CurrencyChangePresenterImpl(mockView)
    }


    @Test
    fun `presenter onReady called, should start polling currency values`() {
        presenter.onReady()

        assert(false)
    }
}