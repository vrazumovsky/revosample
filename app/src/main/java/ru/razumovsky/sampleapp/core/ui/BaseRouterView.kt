package ru.razumovsky.sampleapp.core.ui

import android.support.v4.app.FragmentManager

interface BaseRouterView {
    fun getSupportFragmentManager(): FragmentManager
}