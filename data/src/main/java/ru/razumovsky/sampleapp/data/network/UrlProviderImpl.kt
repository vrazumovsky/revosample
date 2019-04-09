package ru.razumovsky.sampleapp.data.network

import javax.inject.Inject

class UrlProviderImpl @Inject constructor() : UrlProvider {
    override fun getBase(): String = "https://revolut.duckdns.org" // todo move to properties file
}