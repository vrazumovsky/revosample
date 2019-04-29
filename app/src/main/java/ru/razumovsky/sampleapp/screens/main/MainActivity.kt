package ru.razumovsky.sampleapp.screens.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ru.razumovsky.sampleapp.App
import ru.razumovsky.sampleapp.R
import ru.razumovsky.sampleapp.core.ui.BaseRouterView
import ru.razumovsky.sampleapp.screens.main.currencychange.CurrencyChangeFragment
import ru.razumovsky.sampleapp.screens.main.currencychange.CurrencyChangeInjector
import ru.razumovsky.sampleapp.screens.main.currencychange.di.CurrencyChangeModule
import ru.razumovsky.sampleapp.screens.main.di.DaggerMainComponent
import ru.razumovsky.sampleapp.screens.main.di.MainComponent
import ru.razumovsky.sampleapp.screens.main.di.MainModule
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainView, BaseRouterView,
    CurrencyChangeInjector {

    @Inject
    lateinit var presenter: MainPresenter

    lateinit var component: MainComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buildComponent()
        if (savedInstanceState == null) {
            presenter.onCreate()
        }

    }

    private fun buildComponent() {
        component = DaggerMainComponent.builder()
            .appComponent((application as App).component)
            .mainModule(
                MainModule(
                    view = this,
                    routerView = this,
                    containerId = R.id.content
                )
            )
            .build()
        component.inject(this)
    }

    override fun inject(view: CurrencyChangeFragment) {
        component.plus(CurrencyChangeModule(view)).inject(view)
    }
}
