package ru.razumovsky.sampleapp.data.network.api

import io.reactivex.Observable
import retrofit2.http.GET
import ru.razumovsky.sampleapp.data.network.dto.CurrencyRatesResponse

interface CurrencyRatesApi {

    @GET("/latest?base=EUR")
    fun getRates(): Observable<CurrencyRatesResponse>


}