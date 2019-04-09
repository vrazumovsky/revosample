package ru.razumovsky.sampleapp.di.modules

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.razumovsky.sampleapp.data.network.api.CurrencyRatesApi
import ru.razumovsky.sampleapp.di.scope.AppScope

@Module
class ApiModule {

    @Provides
    @AppScope
    fun provideCurrencyApi(retrofit: Retrofit): CurrencyRatesApi =
        retrofit.create(CurrencyRatesApi::class.java)
}