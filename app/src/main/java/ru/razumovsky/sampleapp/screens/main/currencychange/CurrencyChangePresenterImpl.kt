package ru.razumovsky.sampleapp.screens.main.currencychange

import com.github.nitrico.lastadapter.StableId
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import ru.razumovsky.sampleapp.domain.usecase.GetCurrencyRatesUseCase
import javax.inject.Inject

class CurrencyChangePresenterImpl @Inject constructor(
    private val view: CurrencyChangeView,
    private val mapper: CurrencyRateUIMapper,
    private val useCase: GetCurrencyRatesUseCase
) : CurrencyChangePresenter {

    override fun onReady() {
        useCase.execute()
            .map { mapper.map(it) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    val sortedList = sortNewDataLikeOnView(it)
                    view.showCurrencies(sortedList)
                },
                onError = {
                    it.printStackTrace()
                }
            )
    }

    private fun sortNewDataLikeOnView(data: List<StableId>): List<StableId> {
        return data.sortedBy { stableId ->
            val index = view.getCurrencies().indexOfFirst { it.stableId == stableId.stableId }
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