package com.example.multimodulecrypto.core.model

import com.google.gson.annotations.SerializedName


data class CurrentPrice(
    @SerializedName("usd") var usd: Double? = null,
)