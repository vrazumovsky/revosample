package ru.razumovsky.sampleapp.di.modules

import dagger.Module
import dagger.Provides
import ru.razumovsky.sampleapp.di.scope.AppScope
import ru.razumovsky.sampleapp.domain.usecase.GetCurrencyRatesUseCase
import ru.razumovsky.sampleapp.domain.usecase.GetCurrencyRatesUseCaseImpl

@Module
class UseCaseModule {

    @AppScope
    @Provides
    fun provideGetCurrencyRatesUseCase(useCase: GetCurrencyRatesUseCaseImpl): GetCurrencyRatesUseCase = useCase
}


