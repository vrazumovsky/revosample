package ru.razumovsky.sampleapp.data.mapper

import ru.razumovsky.sampleapp.data.entity.CurrencyRates
import ru.razumovsky.sampleapp.data.network.dto.CurrencyRatesResponse

interface CurrencyRatesDtoToEntityMapper {
    fun map(dto: CurrencyRatesResponse): CurrencyRates
}