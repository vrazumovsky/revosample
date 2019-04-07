package ru.razumovsky.sampleapp.screens.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ru.razumovsky.sampleapp.R
import ru.razumovsky.sampleapp.core.ui.BaseRouterView

class MainActivity : AppCompatActivity(), MainView, BaseRouterView {

    private lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        assemble()
        presenter.onCreate()

    }

    private fun assemble() {
        val router = MainRouterImpl(this, R.id.content)
        presenter = MainPresenterImpl(this, router)
    }
}
