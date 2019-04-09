package ru.razumovsky.sampleapp.data.mapper

import ru.razumovsky.sampleapp.data.entity.CurrencyRates
import ru.razumovsky.sampleapp.data.network.dto.CurrencyRatesResponse
import javax.inject.Inject

class CurrencyRatesDtoToEntityMapperImpl @Inject constructor() : CurrencyRatesDtoToEntityMapper {
    override fun map(dto: CurrencyRatesResponse): CurrencyRates {
        return CurrencyRates(
            base = dto.base ?: "",
            date = dto.date ?: "",
            rates = dto.rates ?: emptyMap()
        )
    }

}