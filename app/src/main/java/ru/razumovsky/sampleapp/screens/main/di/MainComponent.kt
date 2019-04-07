package ru.razumovsky.sampleapp.screens.main.di

import dagger.Component
import ru.razumovsky.sampleapp.di.scope.ActivityScope
import ru.razumovsky.sampleapp.screens.main.MainActivity

@ActivityScope
@Component(modules = [MainModule::class])
interface MainComponent {
    fun inject(view: MainActivity)

}
