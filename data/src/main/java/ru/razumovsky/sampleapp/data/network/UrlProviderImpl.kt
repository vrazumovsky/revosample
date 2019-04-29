package ru.razumovsky.sampleapp.data.network

import ru.razumovsky.sampleapp.data.BuildConfig
import javax.inject.Inject

class UrlProviderImpl @Inject constructor() : UrlProvider {
    override fun getBase(): String = BuildConfig.BACKEND_URL
}