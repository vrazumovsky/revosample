package ru.razumovsky.sampleapp.screens.main

import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import ru.razumovsky.sampleapp.core.ui.BaseRouterView

class MainRouterImplTest {

    @Mock
    private lateinit var view: BaseRouterView

    private lateinit var router: MainRouter


    @Test
    fun `router openCurrencyChangeInfo() method called, should call view getSupportFragmentManager()`() {
        router.openCurrencyChangeInfo()
        verify(view).getSupportFragmentManager()
    }
}