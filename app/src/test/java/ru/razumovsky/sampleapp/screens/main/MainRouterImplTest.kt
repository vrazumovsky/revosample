package ru.razumovsky.sampleapp.screens.main

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import ru.razumovsky.sampleapp.core.ui.BaseRouterView

class MainRouterImplTest {

    @Mock
    private lateinit var mockView: BaseRouterView

    @Mock
    private lateinit var mockFragmentManager: FragmentManager

    @Mock
    private lateinit var mockTransaction: FragmentTransaction

    private lateinit var router: MainRouter

    private val mockContainerId = 0


    @Before
    fun initialize() {
        MockitoAnnotations.initMocks(this)

        whenever(mockView.getSupportFragmentManager()).thenReturn(mockFragmentManager)
        whenever(mockFragmentManager.beginTransaction()).thenReturn(mockTransaction)
        whenever(mockTransaction.replace(eq(mockContainerId), any())).thenReturn(mockTransaction)

        router = MainRouterImpl(mockView, mockContainerId)
    }


    @Test
    fun `router openCurrencyChangeInfo() method called, should call view getSupportFragmentManager() and change fragment`() {
        router.openCurrencyChangeInfo()
        verify(mockView).getSupportFragmentManager()
        verify(mockTransaction).replace(eq(mockContainerId), any())
        verify(mockTransaction).commit()
    }
}