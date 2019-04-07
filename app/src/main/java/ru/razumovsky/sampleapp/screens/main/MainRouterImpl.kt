package ru.razumovsky.sampleapp.screens.main

import android.support.v4.app.Fragment
import ru.razumovsky.sampleapp.core.ui.BaseRouterView
import ru.razumovsky.sampleapp.screens.main.currencychange.CurrencyChangeFragment

class MainRouterImpl(
    private val view: BaseRouterView,
    private val containerId: Int
) : MainRouter {

    override fun openCurrencyChangeInfo() {
        val fragment = CurrencyChangeFragment.newInstance()
        replace(fragment)
    }

    private fun replace(fragment: Fragment) {
        view.getSupportFragmentManager()
            .beginTransaction()
            .replace(containerId, fragment)
            .commit()
    }
}