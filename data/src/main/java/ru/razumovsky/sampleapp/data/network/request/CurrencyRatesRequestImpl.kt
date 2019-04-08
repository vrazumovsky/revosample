package ru.razumovsky.sampleapp.data.network.request

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import ru.razumovsky.sampleapp.data.network.api.CurrencyRatesApi
import ru.razumovsky.sampleapp.data.network.dto.CurrencyRatesResponse
import javax.inject.Inject

class CurrencyRatesRequestImpl @Inject constructor(private val api: CurrencyRatesApi) : CurrencyRatesRequest {
    override fun run(): Observable<CurrencyRatesResponse> {
        return api.getRates()
            .subscribeOn(Schedulers.io())

    }

}