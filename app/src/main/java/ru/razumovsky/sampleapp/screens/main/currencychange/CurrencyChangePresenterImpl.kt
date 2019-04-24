package ru.razumovsky.sampleapp.screens.main.currencychange

import com.github.nitrico.lastadapter.StableId
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import ru.razumovsky.sampleapp.domain.usecase.GetCurrencyRatesUseCase
import ru.razumovsky.sampleapp.domain.viewmodel.CurrencyRateViewModel
import javax.inject.Inject

class CurrencyChangePresenterImpl @Inject constructor(
    private val view: CurrencyChangeView,
    private val mapper: CurrencyRateUIMapper,
    private val useCase: GetCurrencyRatesUseCase
) : CurrencyChangePresenter {

    private var polling: Disposable? = null

    override fun onStart() {
        polling = useCase.executePolling()
            .prepareAndShowData()
    }

    override fun onStop() {
        polling?.dispose()
    }

    private fun Observable<List<CurrencyRateViewModel>>.calculateCurrencyValues():
            Observable<List<CurrencyRateViewModel>> = map { currencies ->
        val baseItem = getCurrencies().firstOrNull()
        baseItem?.let {
            baseItem.rate.toFloatOrNull()?.let { calculateValues(it, currencies) } ?: mapToEmptyValues(currencies)
        } ?: currencies
    }

    private fun mapToEmptyValues(currencies: List<CurrencyRateViewModel>): List<CurrencyRateViewModel> =
        currencies.map { CurrencyRateViewModel(it.name, 0f) }

    private fun calculateValues(baseRate: Float, data: List<CurrencyRateViewModel>): List<CurrencyRateViewModel> {
        val first = data.firstOrNull()
        first?.let {
            return data
                .map {
                    val rate = (it.value / first.value) * baseRate
                    CurrencyRateViewModel(it.name, rate)
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

    override fun amountChanged(amount: String) {
        useCase.execute()
            .prepareAndShowData()
    }

    private fun Observable<List<CurrencyRateViewModel>>.prepareAndShowData(): Disposable =
        sortCurrencies()
            .calculateCurrencyValues()
            .map { mapper.map(it) }
            .remainFirstItemUnchanged()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    view.showCurrencies(it)
                    if (it.isEmpty()) {
                        view.showEmptyMessage()
                    }
                },
                onError = {
                    it.printStackTrace()
                }
            )


    private fun Observable<List<StableId>>.remainFirstItemUnchanged():
            Observable<List<StableId>> = map { currencies ->
        val firstOld = getCurrencies().firstOrNull()
        firstOld?.let {
            val mutableList = currencies.toMutableList()
            mutableList.removeAt(0)
            mutableList.add(0, firstOld)
            mutableList
        } ?: currencies
    }


    private fun getCurrencies(): List<CurrencyItem> {
        return view.getCurrencies().filterIsInstance(CurrencyItem::class.java)
    }
}