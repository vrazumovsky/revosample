package ru.razumovsky.sampleapp.domain.usecase

import io.reactivex.Observable
import ru.razumovsky.sampleapp.data.entity.CurrencyRate

interface GetCurrencyRatesUseCase {
    fun execute(): Observable<List<CurrencyRate>>
}