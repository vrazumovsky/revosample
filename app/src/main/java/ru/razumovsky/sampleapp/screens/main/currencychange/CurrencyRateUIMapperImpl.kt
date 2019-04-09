package ru.razumovsky.sampleapp.screens.main.currencychange

import com.github.nitrico.lastadapter.StableId
import ru.razumovsky.sampleapp.domain.viewmodel.CurrencyRateViewModel
import javax.inject.Inject

class CurrencyRateUIMapperImpl @Inject constructor(): CurrencyRateUIMapper {
    override fun map(list: List<CurrencyRateViewModel>): List<StableId> = list.map {
        CurrencyItem(it.name, it.verboseName, it.value)
    }
}