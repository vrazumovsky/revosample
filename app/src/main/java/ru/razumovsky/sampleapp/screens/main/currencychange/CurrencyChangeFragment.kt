package ru.razumovsky.sampleapp.screens.main.currencychange

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.nitrico.lastadapter.LastAdapter
import com.github.nitrico.lastadapter.StableId
import kotlinx.android.synthetic.main.currency_change_fragment.*
import ru.razumovsky.sampleapp.BR
import ru.razumovsky.sampleapp.R
import ru.razumovsky.sampleapp.databinding.CurrencyItemBinding
import javax.inject.Inject


class CurrencyChangeFragment : Fragment(), CurrencyChangeView {

    companion object {
        fun newInstance() : CurrencyChangeFragment {
            return CurrencyChangeFragment()
        }
    }

    private val items: MutableList<StableId> = mutableListOf()

    private val adapter: LastAdapter = LastAdapter(items, BR.item, stableIds = true)

        .map<CurrencyItem, CurrencyItemBinding>(R.layout.currency_item) {
            onClick {
                it.binding.item?.let {
                    presenter.itemClicked(it)
                    recyclerView.scrollToPosition(0)
                }
            }

        }


    @Inject
    lateinit var presenter: CurrencyChangePresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.currency_change_fragment, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter.into(recyclerView)
        buildComponent()
        presenter.onReady()

    }

    override fun showCurrencies(data: List<StableId>) {
        items.clear()
        items.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun getCurrencies(): List<StableId> = items

    private fun buildComponent() {
        (activity as CurrencyChangeInjector).inject(this)
    }
}