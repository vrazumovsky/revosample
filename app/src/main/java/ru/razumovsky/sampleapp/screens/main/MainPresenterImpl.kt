package ru.razumovsky.sampleapp.screens.main

import javax.inject.Inject

class MainPresenterImpl @Inject constructor(
    private val view: MainView,
    private val router: MainRouter
) : MainPresenter {

    override fun onCreate() {
        router.openCurrencyChangeInfo()
    }
}