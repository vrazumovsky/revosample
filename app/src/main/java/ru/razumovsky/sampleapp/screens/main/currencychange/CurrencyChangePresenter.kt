package ru.razumovsky.sampleapp.screens.main.currencychange

interface CurrencyChangePresenter {

    fun onReady()

    fun itemClicked(item: CurrencyItem)

    fun amountChanged(amount: String)
}