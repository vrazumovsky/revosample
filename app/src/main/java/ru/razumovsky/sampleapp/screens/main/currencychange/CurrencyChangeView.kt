package ru.razumovsky.sampleapp.screens.main.currencychange

import com.github.nitrico.lastadapter.StableId

interface CurrencyChangeView {
    fun showCurrencies(data: List<StableId>)

    fun getCurrencies(): List<StableId>

    fun showEmptyMessage()

    fun isScrolling(): Boolean
}