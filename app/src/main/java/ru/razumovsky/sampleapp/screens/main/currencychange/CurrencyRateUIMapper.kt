package ru.razumovsky.sampleapp.screens.main.currencychange

import com.github.nitrico.lastadapter.StableId
import ru.razumovsky.sampleapp.domain.viewmodel.CurrencyRateViewModel

interface CurrencyRateUIMapper {
    fun map(list: List<CurrencyRateViewModel>): List<StableId>
}