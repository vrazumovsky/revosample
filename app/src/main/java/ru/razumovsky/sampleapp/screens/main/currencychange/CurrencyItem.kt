package ru.razumovsky.sampleapp.screens.main.currencychange

import com.github.nitrico.lastadapter.StableId


data class CurrencyItem(val name: String,
                        val verboseName: String,
                        var rate: String) : StableId {
    override val stableId: Long
        get() = name.hashCode().toLong()

}