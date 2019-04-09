package ru.razumovsky.sampleapp.domain.usecase

import io.reactivex.Observable
import ru.razumovsky.sampleapp.data.entity.CurrencyRate
import ru.razumovsky.sampleapp.data.repo.CurrencyRatesRepo
import javax.inject.Inject

class GetCurrencyRatesUseCaseImpl @Inject constructor(
    private val repo: CurrencyRatesRepo
) : GetCurrencyRatesUseCase {
    override fun execute(): Observable<List<CurrencyRate>> {
        return repo.getRates().map {
            it.map { CurrencyRate(it.key, it.value) }
        }
    }

}