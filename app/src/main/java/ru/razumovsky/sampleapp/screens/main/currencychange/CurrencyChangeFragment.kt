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
import com.github.nitrico.lastadapter.Holder


class CurrencyChangeFragment : BaseFragment(), CurrencyChangeView {

    companion object {
        fun newInstance(): CurrencyChangeFragment {
            return CurrencyChangeFragment()
        }

        private const val EMPTY_MESSAGE = "Пусто"
    }

    private var isScrolling = false

    override fun isScrolling(): Boolean = isScrolling

    private var firstItemEditTextSubscription: Disposable? = null

    private val items: MutableList<StableId> = mutableListOf()

    private val adapter: LastAdapter by lazy {
        LastAdapter(items, BR.item, stableIds = true)

            .map<CurrencyItem, CurrencyItemBinding>(R.layout.currency_item) {
                onClick {
                    onItemClicked(it)
                }

                onBind {
                    if (it.adapterPosition == 0) {
                        listenToTextChanges(it.binding.amount)
                        hideKeyboardByPressingDone(it.binding.amount)
                        disableForwardEditTextClickToItem(it)
                    } else {
                        forwardEditTextClickToItem(it)
                    }
                }

                onRecycle {
                    if (it.adapterPosition == 0) {
                        firstItemEditTextSubscription?.dispose()
                    }
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

        handleScrollState()
        initProgressBar()
    }

    private fun initProgressBar() {
        progressbar.visibility = if (items.isEmpty()) View.VISIBLE else View.GONE
    }

    override fun onStart() {
        super.onStart()
        presenter.onStart()
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }

    private fun disableForwardEditTextClickToItem(holder: Holder<CurrencyItemBinding>) {
        holder.binding.amount.setOnTouchListener(null)
    }

    private fun forwardEditTextClickToItem(holder: Holder<CurrencyItemBinding>) {
        holder.binding.amount.setOnTouchListener { _, _ ->
            onItemClicked(holder)
            true
        }
    }

    private fun onItemClicked(holder: Holder<CurrencyItemBinding>) {
        holder.binding.item?.let {
            presenter.itemClicked(it)
            recyclerView.scrollToPosition(0)
        }
        holder.binding.amount.apply {
            requestFocus()
            setSelection(text.length)
        }
        showKeyboard()
    }

    private fun listenToTextChanges(editText: EditText) {
        firstItemEditTextSubscription?.dispose()
        firstItemEditTextSubscription = RxTextView.textChanges(editText)
            .skipInitialValue()
            .subscribeBy { presenter.amountChanged(it.toString()) }
    }

    private fun handleScrollState() {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                isScrolling = newState != RecyclerView.SCROLL_STATE_IDLE
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
        progressbar.visibility = View.GONE
        errorTextView.visibility = View.GONE
    }

    override fun showEmptyMessage() {
        errorTextView.visibility = View.VISIBLE
        errorTextView.text = EMPTY_MESSAGE
    }

    override fun getCurrencies(): List<StableId> = items

    private fun buildComponent() {
        (activity as CurrencyChangeInjector).inject(this)
    }
}