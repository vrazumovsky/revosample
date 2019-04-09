package ru.razumovsky.sampleapp.screens.main.currencychange.di

import dagger.Module
import dagger.Provides
import ru.razumovsky.sampleapp.di.scope.FragmentScope
import ru.razumovsky.sampleapp.screens.main.currencychange.*

@Module
class CurrencyChangeModule(val view: CurrencyChangeView) {

    @FragmentScope
    @Provides
    fun provideView(): CurrencyChangeView = view

    @Provides
    @FragmentScope
    fun providePresenter(presenter: CurrencyChangePresenterImpl): CurrencyChangePresenter = presenter

    @Provides
    @FragmentScope
    fun provideUIMapper(mapper: CurrencyRateUIMapperImpl): CurrencyRateUIMapper = mapper
}