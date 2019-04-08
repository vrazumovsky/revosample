package ru.razumovsky.sampleapp.data.repo

import io.reactivex.Observable
import ru.razumovsky.sampleapp.data.entity.CurrencyRate

interface CurrencyRatesRepo {

    fun getRates(): Observable<Map<String, Float>>
}