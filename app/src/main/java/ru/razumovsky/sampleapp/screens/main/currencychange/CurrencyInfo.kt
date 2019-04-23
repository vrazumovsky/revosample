package ru.razumovsky.sampleapp.screens.main.currencychange

import ru.razumovsky.sampleapp.R


enum class CurrencyInfo(val value: String,
                        val verboseName: String,
                        val imageResource: Int) {


    USD("USD", "US Dollar", R.drawable.usd),
    EUR("EUR", "Euro", R.drawable.euro),
    GBP("GBP", "The pound sterling", R.drawable.gbp),
    AUD("AUD", "Australian Dollar", R.drawable.aud),
    BGN("BGN", "Bulgaria Lev", R.drawable.bgn),
    BRL("BRL", "Brazilian Real", R.drawable.brl),
    CAD("CAD", "Canadian Dollar", R.drawable.cad),
    CHF("CHF", "Swiss Franc", R.drawable.chf),
    CNY("CNY", "Chinese Yuan Renminbi", R.drawable.cny),
    CZK("CZK", "Czech Koruna", R.drawable.czk),
    DKK("DKK", "Danish Krone", R.drawable.dkk),
    HKD("HKD", "Hong Kong Dollar", R.drawable.hkd),
    HRK("HRK", "Croatian Kuna", R.drawable.hrk),
    HUF("HUF", "Hungarian Forint", R.drawable.huf),
    IDR("IDR", "Indonesian Rupiah", R.drawable.idr),
    ILS("ILS", "Israeli Shekel", R.drawable.ils),
    INR("INR", "Indian Rupee", R.drawable.inr),
    ISK("ISK", "Icelandic Krona", R.drawable.isk),
    JPY("JPY", "Japanese Yen", R.drawable.jpy),
    KRW("KRW", "South Korean Won", R.drawable.krw),
    MXN("MXN", "Mexican Peso", R.drawable.mxn),
    MYR("MYR", "Malaysian Ringgit", R.drawable.myr),
    NOK("NOK", "Norwegian Krone", R.drawable.nok),
    NZD("NZD", "New Zealand Dollar", R.drawable.nzd),
    PHP("PHP", "Philippine Peso", R.drawable.php),
    PLN("PLN", "Polish Zloty", R.drawable.pln),
    RON("RON", "Romanian Leu", R.drawable.ron),
    RUB("RUB", "Russian Ruble", R.drawable.rub),
    SEK("SEK", "Swedish Krona", R.drawable.sek),
    SGD("SGD", "Singapore Dollar", R.drawable.sgd),
    THB("THB", "Thai Baht", R.drawable.thb),
    TRY("TRY", "Turkish Lira", R.drawable.try_currency),
    ZAR("ZAR", "South African Rand", R.drawable.zar),
    UNKNOWN("UNKNOWN", "Unknown", R.drawable.unknown);

    companion object {
        fun find(value: String): CurrencyInfo = CurrencyInfo.values().find { it.value == value } ?: CurrencyInfo.UNKNOWN
    }

}