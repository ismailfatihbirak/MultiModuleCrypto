package com.example.multimodulecrypto.core.model

import com.google.gson.annotations.SerializedName


data class RootId (

  @SerializedName("id"                              ) var id                           : String?           = null,
  @SerializedName("symbol"                          ) var symbol                       : String?           = null,
  @SerializedName("name"                            ) var name                         : String?           = null,
  @SerializedName("hashing_algorithm"               ) var hashingAlgorithm             : String?           = null,
  @SerializedName("description"                     ) var description                  : Description?      = Description(),
  @SerializedName("image"                           ) var image                        : Image?            = Image(),
  @SerializedName("market_data"                     ) var marketData                   : MarketData?       = MarketData(),

)