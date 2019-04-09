package ru.razumovsky.sampleapp.di.modules

import dagger.Module
import dagger.Provides
import ru.razumovsky.sampleapp.data.mapper.CurrencyRatesDtoToEntityMapper
import ru.razumovsky.sampleapp.data.mapper.CurrencyRatesDtoToEntityMapperImpl
import ru.razumovsky.sampleapp.di.scope.AppScope

@Module
class MapperModule {

    @AppScope
    @Provides
    fun provideCurrencyRatesDtoToEntityMapper(mapper: CurrencyRatesDtoToEntityMapperImpl):
            CurrencyRatesDtoToEntityMapper = mapper
}