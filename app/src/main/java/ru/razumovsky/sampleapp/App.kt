package ru.razumovsky.sampleapp

import android.app.Application
import ru.razumovsky.sampleapp.di.components.AppComponent
import ru.razumovsky.sampleapp.di.components.DaggerAppComponent
import ru.razumovsky.sampleapp.di.modules.*

class App : Application() {

    val component: AppComponent by lazy {
        DaggerAppComponent.builder()
            .repoModule(RepoModule())
            .networkModule(NetworkModule())
            .apiModule(ApiModule())
            .mapperModule(MapperModule())
            .useCaseModule(UseCaseModule())
            .build()
    }
}