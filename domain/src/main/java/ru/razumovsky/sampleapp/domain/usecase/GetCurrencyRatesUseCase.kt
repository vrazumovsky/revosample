package ru.razumovsky.sampleapp.domain.usecase

import io.reactivex.Observable
import ru.razumovsky.sampleapp.domain.viewmodel.CurrencyRateViewModel

interface GetCurrencyRatesUseCase {
    fun execute(): Observable<List<CurrencyRateViewModel>>
}