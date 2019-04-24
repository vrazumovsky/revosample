package ru.razumovsky.sampleapp.screens.main.currencychange

import com.github.nitrico.lastadapter.StableId
import ru.razumovsky.sampleapp.domain.viewmodel.CurrencyRateViewModel
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import javax.inject.Inject

class CurrencyRateUIMapperImpl @Inject constructor(): CurrencyRateUIMapper {
    override fun map(list: List<CurrencyRateViewModel>): List<StableId> = list.mapIndexed { index, it ->
        val otherSymbols = DecimalFormatSymbols()
        otherSymbols.decimalSeparator = '.'
        val formatter = DecimalFormat("#.##", otherSymbols)
        formatter.minimumFractionDigits = 2
        formatter.maximumFractionDigits = 2

        val value = if (it.value == 0f) "" else formatter.format(it.value)
        val currencyInfo = CurrencyInfo.find(it.name)
        CurrencyItem(it.name, currencyInfo.verboseName, currencyInfo.imageResource, value)
    }
}