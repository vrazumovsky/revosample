package ru.razumovsky.sampleapp.domain.usecase

import io.reactivex.Observable
import ru.razumovsky.sampleapp.data.entity.Currency
import ru.razumovsky.sampleapp.data.repo.CurrencyRatesRepo
import ru.razumovsky.sampleapp.domain.viewmodel.CurrencyRateViewModel
import javax.inject.Inject

class GetCurrencyRatesUseCaseImpl @Inject constructor(
    private val repo: CurrencyRatesRepo
) : GetCurrencyRatesUseCase {
    override fun execute(): Observable<List<CurrencyRateViewModel>> {
        return repo.getRates().map {
            it.map {
                CurrencyRateViewModel(
                    name = it.key,
                    verboseName = Currency.find(it.key).verboseName,
                    value = it.value
                )
            }
        }
    }

}