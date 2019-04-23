package ru.razumovsky.sampleapp.domain.usecase

import io.reactivex.Observable
import ru.razumovsky.sampleapp.data.entity.Currency
import ru.razumovsky.sampleapp.data.repo.CurrencyRatesRepo
import ru.razumovsky.sampleapp.domain.viewmodel.CurrencyRateViewModel
import javax.inject.Inject

class GetCurrencyRatesUseCaseImpl @Inject constructor(
    private val repo: CurrencyRatesRepo
) : GetCurrencyRatesUseCase {

    override fun executePolling(): Observable<List<CurrencyRateViewModel>> {
        return repo.getRates()
            .mapRates()
            .makeEuroFirstCurrency()
    }

    override fun execute(): Observable<List<CurrencyRateViewModel>> {
        return repo.getRatesSingle()
            .toObservable()
            .mapRates()
            .makeEuroFirstCurrency()
    }

    private fun Observable<Map<String, Float>>.mapRates():
            Observable<List<CurrencyRateViewModel>> = map {
        it.map {
            CurrencyRateViewModel(
                name = it.key,
                value = it.value
            )
        }
    }

    private fun Observable<List<CurrencyRateViewModel>>.makeEuroFirstCurrency():
            Observable<List<CurrencyRateViewModel>> = map {
        makeEuroFirstCurrency(it)
    }

    private fun makeEuroFirstCurrency(data: List<CurrencyRateViewModel>): List<CurrencyRateViewModel> {
        val euro = data.find { it.name == Currency.EUR.value }

        return mutableListOf<CurrencyRateViewModel>().apply {
            addAll(data)
            euro?.let {
                remove(euro)
                add(0, euro)
            }
        }
    }
}