package ru.razumovsky.sampleapp.data.mapper

import ru.razumovsky.sampleapp.data.entity.CurrencyRates
import ru.razumovsky.sampleapp.data.network.dto.CurrencyRatesResponse

class CurrencyRatesDtoToEntityMapperImpl : CurrencyRatesDtoToEntityMapper {
    override fun map(dto: CurrencyRatesResponse): CurrencyRates {
        return CurrencyRates(
            base = dto.base ?: "",
            date = dto.date ?: "",
            rates = dto.rates ?: emptyMap()
        )
    }

}