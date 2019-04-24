package ru.razumovsky.sampleapp.screens.main.currencychange

import com.github.nitrico.lastadapter.StableId
import ru.razumovsky.sampleapp.domain.viewmodel.CurrencyRateViewModel
import javax.inject.Inject

class CurrencyRateUIMapperImpl @Inject constructor(): CurrencyRateUIMapper {
    override fun map(list: List<CurrencyRateViewModel>): List<StableId> = list.mapIndexed { index, it ->
        val value = if (it.value == 0f) "" else String.format("%.2f", it.value).replace(",", ".")
        val currencyInfo = CurrencyInfo.find(it.name)
        CurrencyItem(it.name, currencyInfo.verboseName, currencyInfo.imageResource, value)
    }
}