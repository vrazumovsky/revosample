package ru.razumovsky.sampleapp.screens.main.currencychange

import com.github.nitrico.lastadapter.StableId
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import ru.razumovsky.sampleapp.domain.usecase.GetCurrencyRatesUseCase
import ru.razumovsky.sampleapp.domain.viewmodel.CurrencyRateViewModel
import javax.inject.Inject

class CurrencyChangePresenterImpl @Inject constructor(
    private val view: CurrencyChangeView,
    private val mapper: CurrencyRateUIMapper,
    private val useCase: GetCurrencyRatesUseCase
) : CurrencyChangePresenter {

    override fun onReady() {
        useCase.executePolling()
            .sortCurrencies()
            .calculateCurrencyValues()
            .map { mapper.map(it) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    view.showCurrencies(it)
                },
                onError = {
                    it.printStackTrace()
                }
            )
    }

    private fun Observable<List<CurrencyRateViewModel>>.calculateCurrencyValues():
            Observable<List<CurrencyRateViewModel>> = map {
            val baseItem = getCurrencies().firstOrNull()
            if (baseItem != null) {
                calculateValues(baseItem.rate.toFloat(), it)
            } else {
                it
            }
        }

    private fun calculateValues(baseRate: Float, data: List<CurrencyRateViewModel>): List<CurrencyRateViewModel> {
        val first = data.firstOrNull()
        first?.let {
            return data
                .map {
                    val rate = (it.value / first.value) * baseRate
                    CurrencyRateViewModel(it.name, it.verboseName, rate)
                }
        }
        return data
    }

    private fun Observable<List<CurrencyRateViewModel>>.sortCurrencies():
            Observable<List<CurrencyRateViewModel>> = map { sortNewDataLikeOnView(it) }


    private fun sortNewDataLikeOnView(data: List<CurrencyRateViewModel>): List<CurrencyRateViewModel> {
        val currencies = getCurrencies()
        return data.sortedBy { item ->
            val index = currencies.indexOfFirst { it.name == item.name }
            if (index == -1) {
                Int.MAX_VALUE
            } else {
                index
            }
        }
    }

    override fun itemClicked(item: CurrencyItem) {
        val list = mutableListOf<StableId>()
        list.addAll(view.getCurrencies())
        list.remove(item)
        list.add(0, item)
        view.showCurrencies(list)
    }

}