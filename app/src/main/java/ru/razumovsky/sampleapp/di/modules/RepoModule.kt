package ru.razumovsky.sampleapp.di.modules

import dagger.Module
import dagger.Provides
import ru.razumovsky.sampleapp.data.repo.CurrencyRatesRepo
import ru.razumovsky.sampleapp.data.repo.CurrencyRatesServerPollingRepoImpl
import ru.razumovsky.sampleapp.di.scope.AppScope

@Module
class RepoModule {

    @Provides
    @AppScope
    fun provideCurrencyRatesRepo(repo: CurrencyRatesServerPollingRepoImpl): CurrencyRatesRepo = repo

}