package com.example.multimodulecrypto.core.model

import com.google.gson.annotations.SerializedName

data class Root (

  @SerializedName("id"                               ) var id                           : String?        = null,
  @SerializedName("symbol"                           ) var symbol                       : String?        = null,
  @SerializedName("name"                             ) var name                         : String?        = null,
  @SerializedName("image"                            ) var image                        : String?        = null,
  @SerializedName("current_price"                    ) var currentPrice                 : Double?        = null,
  @SerializedName("high_24h"                         ) var high24h                      : Double?        = null,
  @SerializedName("low_24h"                          ) var low24h                       : Double?        = null,
  @SerializedName("price_change_24h"                 ) var priceChange24h               : Double?        = null,
  @SerializedName("price_change_percentage_24h"      ) var priceChangePercentage24h     : Double?        = null,
  @SerializedName("last_updated"                     ) var lastUpdated                  : String?        = null,
  @SerializedName("sparkline_in_7d"                  ) var sparklineIn7d                : SparklineIn7d? = SparklineIn7d()

)