package ru.razumovsky.sampleapp.data.network.request

import io.reactivex.Observable
import ru.razumovsky.sampleapp.data.network.dto.CurrencyRatesResponse

interface CurrencyRatesRequest {
    fun run(): Observable<CurrencyRatesResponse>
}