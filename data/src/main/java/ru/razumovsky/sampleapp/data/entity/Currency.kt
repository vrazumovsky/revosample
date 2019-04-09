package ru.razumovsky.sampleapp.data.entity

enum class Currency(val value: String,
                    val verboseName: String) {


    USD("USD", "US Dollar"),
    EUR("EUR", "Euro"),
    AUD("AUD", "Australian Dollar"),
    BGN("BGN", "Bulgarian lev"),
    UNKNOWN("UNKNOWN", "Unknown");

    companion object {
        fun find(value: String): Currency = Currency.values().find { it.value == value } ?: UNKNOWN
    }

    //todo add missing currencies
}