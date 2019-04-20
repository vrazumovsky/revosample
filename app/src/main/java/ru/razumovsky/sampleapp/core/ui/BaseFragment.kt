package ru.razumovsky.sampleapp.core.ui

import android.content.Context
import android.support.v4.app.Fragment
import android.view.inputmethod.InputMethodManager

open class BaseFragment : Fragment() {

    protected fun showKeyboard() {
        val inputManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputManager?.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0)
    }

    protected fun hideKeyboard() {
        val inputManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        val view = activity?.currentFocus ?: return
        inputManager?.hideSoftInputFromWindow(view.windowToken, 0)
    }
}