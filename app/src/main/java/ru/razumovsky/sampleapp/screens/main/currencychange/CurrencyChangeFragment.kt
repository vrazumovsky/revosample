package ru.razumovsky.sampleapp.screens.main.currencychange

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.razumovsky.sampleapp.R


class CurrencyChangeFragment : Fragment(), CurrencyChangeView {

    companion object {
        fun newInstance() : CurrencyChangeFragment {
            return CurrencyChangeFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.currency_change_fragment, container, false)
        return view
    }
}