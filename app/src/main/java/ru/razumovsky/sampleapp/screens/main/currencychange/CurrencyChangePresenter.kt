package ru.razumovsky.sampleapp.screens.main.currencychange

interface CurrencyChangePresenter {

    fun onStart()

    fun onStop()

    fun itemClicked(item: CurrencyItem)

    fun amountChanged(amount: String)
}