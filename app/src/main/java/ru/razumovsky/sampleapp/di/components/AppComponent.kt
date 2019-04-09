package ru.razumovsky.sampleapp.di.components

import dagger.Component
import ru.razumovsky.sampleapp.di.modules.*
import ru.razumovsky.sampleapp.di.scope.AppScope
import ru.razumovsky.sampleapp.domain.usecase.GetCurrencyRatesUseCase

@AppScope
@Component(
    modules = [
        NetworkModule::class,
        ApiModule::class,
        RepoModule::class,
        RequestModule::class,
        MapperModule::class,
        UseCaseModule::class]
)
interface AppComponent {

    fun provideGetCurrencyRatesUseCase(): GetCurrencyRatesUseCase
}