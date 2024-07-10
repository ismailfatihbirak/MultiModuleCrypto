package com.example.multimodulecrypto.core.model

import com.google.gson.annotations.SerializedName


data class MarketData (

  @SerializedName("current_price"                                ) var currentPrice                           : CurrentPrice?                           = CurrentPrice(),
  @SerializedName("price_change_percentage_7d"                   ) var priceChangePercentage7d                : Double?                                 = null,
  @SerializedName("sparkline_7d"                                 ) var sparkline7d                            : Sparkline7d?                            = Sparkline7d(),

)