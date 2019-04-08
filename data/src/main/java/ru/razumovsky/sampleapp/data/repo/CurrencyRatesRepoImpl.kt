package ru.razumovsky.sampleapp.data.repo

import io.reactivex.Observable
import ru.razumovsky.sampleapp.data.mapper.CurrencyRatesDtoToEntityMapper
import ru.razumovsky.sampleapp.data.network.request.CurrencyRatesRequest
import javax.inject.Inject

class CurrencyRatesRepoImpl @Inject constructor(
    private val request: CurrencyRatesRequest,
    private val mapper: CurrencyRatesDtoToEntityMapper
) : CurrencyRatesRepo {

    override fun getRates(): Observable<Map<String, Float>> {
        return request.run()
            .map { mapper.map(it) }
            .map { it.rates.toMutableMap().apply {
                put(it.base, 1f)
            } }
    }

}