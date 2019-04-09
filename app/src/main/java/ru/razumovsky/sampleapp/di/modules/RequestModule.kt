package ru.razumovsky.sampleapp.di.modules

import dagger.Module
import dagger.Provides
import ru.razumovsky.sampleapp.data.network.request.CurrencyRatesRequest
import ru.razumovsky.sampleapp.data.network.request.CurrencyRatesRequestImpl
import ru.razumovsky.sampleapp.di.scope.AppScope

@Module
class RequestModule {

    @Provides
    @AppScope
    fun provideCurrencyRatesRequest(request: CurrencyRatesRequestImpl): CurrencyRatesRequest = request
}