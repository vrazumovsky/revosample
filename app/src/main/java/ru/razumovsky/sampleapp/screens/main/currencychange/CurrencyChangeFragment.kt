package ru.razumovsky.sampleapp.screens.main.currencychange

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.nitrico.lastadapter.LastAdapter
import com.github.nitrico.lastadapter.StableId
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.currency_change_fragment.*
import ru.razumovsky.sampleapp.BR
import ru.razumovsky.sampleapp.R
import ru.razumovsky.sampleapp.core.ui.BaseFragment
import ru.razumovsky.sampleapp.databinding.CurrencyItemBinding
import javax.inject.Inject
import android.view.inputmethod.EditorInfo
import android.widget.EditText


class CurrencyChangeFragment : BaseFragment(), CurrencyChangeView {

    companion object {
        fun newInstance(): CurrencyChangeFragment {
            return CurrencyChangeFragment()
        }
    }

    private var firstItemEditTextSubscription: Disposable? = null

    private val items: MutableList<StableId> = mutableListOf()

    private val adapter: LastAdapter = LastAdapter(items, BR.item, stableIds = true)

        .map<CurrencyItem, CurrencyItemBinding>(R.layout.currency_item) {
            onClick {
                it.binding.item?.let {
                    presenter.itemClicked(it)
                    recyclerView.scrollToPosition(0)
                }
                it.binding.amount.requestFocus()
                showKeyboard()
            }

            onBind {
                if (it.adapterPosition == 0) {
                    listenToTextChanges(it.binding.amount)
                    hideKeyboardByPressingDone(it.binding.amount)
                }
            }

            onRecycle {
                if (it.adapterPosition == 0) {
                    firstItemEditTextSubscription?.dispose()
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

        hideKeyboardOnScroll()
    }

    private fun listenToTextChanges(editText: EditText) {
        firstItemEditTextSubscription?.dispose()
        firstItemEditTextSubscription = RxTextView.textChanges(editText)
            .skipInitialValue()
            .subscribeBy { presenter.amountChanged(it.toString()) }
    }

    private fun hideKeyboardOnScroll() {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                hideKeyboard()
            }
        })
    }

    private fun hideKeyboardByPressingDone(editText: EditText) {
        editText.setOnEditorActionListener { v, actionId, event ->
            val isDoneButtonPressed = actionId == EditorInfo.IME_ACTION_DONE
            if (isDoneButtonPressed) {
                hideKeyboard()
            }
            isDoneButtonPressed
        }
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