package ru.razumovsky.sampleapp.screens.main.currencychange

import com.github.nitrico.lastadapter.StableId

data class CurrencyItem(val name: String,
                        val verboseName: String,
                        val rate: Float) : StableId {
    override val stableId: Long
        get() = name.hashCode().toLong() + rate.toLong()
}