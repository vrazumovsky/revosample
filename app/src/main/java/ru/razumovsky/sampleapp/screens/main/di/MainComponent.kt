package ru.razumovsky.sampleapp.screens.main.di

import dagger.Component
import ru.razumovsky.sampleapp.di.components.AppComponent
import ru.razumovsky.sampleapp.di.scope.ActivityScope
import ru.razumovsky.sampleapp.screens.main.MainActivity
import ru.razumovsky.sampleapp.screens.main.currencychange.di.CurrencyChangeComponent
import ru.razumovsky.sampleapp.screens.main.currencychange.di.CurrencyChangeModule

@ActivityScope
@Component(modules = [MainModule::class], dependencies = [AppComponent::class])
interface MainComponent {
    fun inject(view: MainActivity)

    fun plus(module: CurrencyChangeModule): CurrencyChangeComponent
}
