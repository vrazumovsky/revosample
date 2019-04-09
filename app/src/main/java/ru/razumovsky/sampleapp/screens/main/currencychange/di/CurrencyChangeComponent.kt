package ru.razumovsky.sampleapp.screens.main.currencychange.di

import dagger.Subcomponent
import ru.razumovsky.sampleapp.di.scope.FragmentScope
import ru.razumovsky.sampleapp.screens.main.currencychange.CurrencyChangeFragment

@FragmentScope
@Subcomponent(modules = [CurrencyChangeModule::class])
interface CurrencyChangeComponent {

    fun inject(view: CurrencyChangeFragment)
}