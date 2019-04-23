package ru.razumovsky.sampleapp.data.entity

enum class Currency(val value: String) {


    USD("USD"),
    EUR("EUR"),
    GBP("GBP"),
    AUD("AUD"),
    BGN("BGN"),
    UNKNOWN("UNKNOWN");

    //todo add missing currencies
}