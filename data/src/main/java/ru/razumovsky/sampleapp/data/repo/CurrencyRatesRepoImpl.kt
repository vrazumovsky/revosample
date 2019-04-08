package ru.razumovsky.sampleapp.data.repo

import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import ru.razumovsky.sampleapp.data.entity.CurrencyRate
import ru.razumovsky.sampleapp.data.network.request.CurrencyRatesRequest
import javax.inject.Inject

class CurrencyRatesRepoImpl @Inject constructor(private val request: CurrencyRatesRequest) : CurrencyRatesRepo {

    override fun getRates(): Observable<List<CurrencyRate>> {
        return request.run()
            .map { it.rates?.map { CurrencyRate(it.key, it.value) } }
    }

}