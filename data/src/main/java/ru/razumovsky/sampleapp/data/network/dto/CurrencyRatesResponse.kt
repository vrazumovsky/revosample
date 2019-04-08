package ru.razumovsky.sampleapp.data.network.dto

import com.google.gson.annotations.SerializedName

class CurrencyRatesResponse(
    @SerializedName("base") val base: String?,
    @SerializedName("date") val date: String?,

    @SerializedName("rates")
    val rates: Map<String, Float>?

)