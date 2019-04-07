package ru.razumovsky.sampleapp.screens.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ru.razumovsky.sampleapp.R
import ru.razumovsky.sampleapp.core.ui.BaseRouterView
import ru.razumovsky.sampleapp.screens.main.di.DaggerMainComponent
import ru.razumovsky.sampleapp.screens.main.di.MainModule
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainView, BaseRouterView {

    @Inject
    lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buildComponent()
        presenter.onReady()

    }


    private fun buildComponent() {
        DaggerMainComponent.builder()
            .mainModule(
                MainModule(
                    view = this,
                    routerView = this,
                    containerId = R.id.content
                )
            )
            .build().inject(this)
    }
}
