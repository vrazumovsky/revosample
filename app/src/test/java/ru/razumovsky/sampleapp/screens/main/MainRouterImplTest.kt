package ru.razumovsky.sampleapp.screens.main

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import ru.razumovsky.sampleapp.core.ui.BaseRouterView

class MainRouterImplTest {

    @Mock
    private lateinit var view: BaseRouterView

    private lateinit var router: MainRouter


    @Before
    fun initialize() {
        MockitoAnnotations.initMocks(this)
        
        router = MainRouterImpl(view)
    }


    @Test
    fun `router openCurrencyChangeInfo() method called, should call view getSupportFragmentManager()`() {
        router.openCurrencyChangeInfo()
        verify(view).getSupportFragmentManager()
    }
}