package ru.razumovsky.sampleapp.screens.main

import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class MainPresenterImplTest {

    @Mock
    private lateinit var mockView: MainView

    @Mock
    private lateinit var mockRouter: MainRouter

    private lateinit var presenter: MainPresenter

    @Before
    fun initialize() {
        MockitoAnnotations.initMocks(this)

        presenter = MainPresenterImpl(mockView, mockRouter)
    }


    @Test
    fun `presenter onCreate called, should call router open currency change screen`() {
        presenter.onCreate()
        verify(mockRouter).openCurrencyChangeInfo()
    }
}