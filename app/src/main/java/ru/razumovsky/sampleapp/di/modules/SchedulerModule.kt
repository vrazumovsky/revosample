package ru.razumovsky.sampleapp.di.modules

import dagger.Module
import dagger.Provides
import ru.razumovsky.sampleapp.data.scheduler.SchedulerProvider
import ru.razumovsky.sampleapp.data.scheduler.SchedulerProviderImpl
import ru.razumovsky.sampleapp.di.scope.AppScope

@Module
class SchedulerModule {

    @Provides
    @AppScope
    fun provideSchedulerProvider(schedulerProvider: SchedulerProviderImpl): SchedulerProvider = schedulerProvider

}