package ru.razumovsky.sampleapp.data.repo

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import ru.razumovsky.sampleapp.data.mapper.CurrencyRatesDtoToEntityMapper
import ru.razumovsky.sampleapp.data.network.request.CurrencyRatesRequest
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CurrencyRatesServerPollingRepoImpl @Inject constructor(
    private val request: CurrencyRatesRequest,
    private val mapper: CurrencyRatesDtoToEntityMapper

) : CurrencyRatesRepo {

    private val ratesCache: MutableMap<String, Float> = mutableMapOf()

    override fun getRates(): Observable<Map<String, Float>> {
        return Observable.interval(1, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.newThread())
            .flatMap { sendGetRatesRequest(it) }
            .scan { t1: PollingResult, t2: PollingResult ->
                if (t2.counter > t1.counter) t2 else PollingResult(t1.counter, t1.result, false)
            }
            .filter { it.isActual }
            .map { it.result }
            .retry()
            .doOnNext {
                ratesCache.clear()
                ratesCache.putAll(it)
            }
    }

    override fun getRatesSingle(): Single<Map<String, Float>> = Single.fromCallable { ratesCache }

    private fun sendGetRatesRequest(counter: Long): Observable<PollingResult> {
        return request.run()
            .subscribeOn(Schedulers.newThread())
            .map { mapper.map(it) }
            .map { it.rates.toMutableMap().apply { put(it.base, 1f) } }
            .map { PollingResult(counter, it, true) }
    }

    private class PollingResult(
        val counter: Long,
        val result: Map<String, Float>,
        val isActual: Boolean)

}