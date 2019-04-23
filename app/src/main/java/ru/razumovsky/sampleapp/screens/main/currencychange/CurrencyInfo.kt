package ru.razumovsky.sampleapp.screens.main.currencychange

import ru.razumovsky.sampleapp.R


enum class CurrencyInfo(val value: String,
                        val verboseName: String,
                        val imageResource: Int) {


    USD("USD", "US Dollar", R.drawable.usd),
    EUR("EUR", "Euro", R.drawable.euro),
    GBP("GBP", "The pound sterling", R.drawable.gbp),
    UNKNOWN("UNKNOWN", "Unknown", R.drawable.unknown);

    companion object {
        fun find(value: String): CurrencyInfo = CurrencyInfo.values().find { it.value == value } ?: CurrencyInfo.UNKNOWN
    }

}