package ru.razumovsky.sampleapp.screens.main.di

import dagger.Module
import dagger.Provides
import ru.razumovsky.sampleapp.core.ui.BaseRouterView
import ru.razumovsky.sampleapp.di.scope.ActivityScope
import ru.razumovsky.sampleapp.screens.main.*

@Module
class MainModule(private val view: MainView,
                 private val routerView: BaseRouterView,
                 private val containerId: Int) {


    @Provides
    @ActivityScope
    fun provideView(): MainView = view

    @Provides
    @ActivityScope
    fun provideRouterView(): BaseRouterView = routerView

    @Provides
    @ActivityScope
    fun provideContainerId(): Int = containerId

    @Provides
    @ActivityScope
    fun provideRouter(router: MainRouterImpl): MainRouter = router

    @Provides
    @ActivityScope
    fun providePresenter(presenter: MainPresenterImpl): MainPresenter = presenter

}