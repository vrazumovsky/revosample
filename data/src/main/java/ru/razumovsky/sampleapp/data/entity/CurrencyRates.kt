package ru.razumovsky.sampleapp.data.entity

class CurrencyRates(
    val base: String,
    val date: String,
    val rates: Map<String, Float>
)