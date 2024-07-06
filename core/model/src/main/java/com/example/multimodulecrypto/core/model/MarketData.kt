package com.example.multimodulecrypto.core.model

import com.google.gson.annotations.SerializedName


data class MarketData (

  @SerializedName("current_price"                                ) var currentPrice                           : CurrentPrice?                           = CurrentPrice(),
  @SerializedName("price_change_24h"                             ) var priceChange24h                         : Double?                                 = null,
  @SerializedName("price_change_percentage_24h"                  ) var priceChangePercentage24h               : Double?                                 = null,
  @SerializedName("price_change_percentage_7d"                   ) var priceChangePercentage7d                : Double?                                 = null,
  @SerializedName("price_change_percentage_14d"                  ) var priceChangePercentage14d               : Double?                                 = null,
  @SerializedName("price_change_percentage_30d"                  ) var priceChangePercentage30d               : Double?                                 = null,
  @SerializedName("price_change_percentage_60d"                  ) var priceChangePercentage60d               : Double?                                 = null,
  @SerializedName("price_change_percentage_200d"                 ) var priceChangePercentage200d              : Double?                                 = null,
  @SerializedName("price_change_percentage_1y"                   ) var priceChangePercentage1y                : Double?                                 = null,
  @SerializedName("market_cap_change_24h"                        ) var marketCapChange24h                     : Double?                                 = null,
  @SerializedName("market_cap_change_percentage_24h"             ) var marketCapChangePercentage24h           : Double?                                 = null,
  @SerializedName("total_supply"                                 ) var totalSupply                            : Int?                                    = null,
  @SerializedName("max_supply"                                   ) var maxSupply                              : Int?                                    = null,
  @SerializedName("circulating_supply"                           ) var circulatingSupply                      : Int?                                    = null,
  @SerializedName("last_updated"                                 ) var lastUpdated                            : String?                                 = null,
  @SerializedName("sparkline_7d"                                 ) var sparkline7d                            : Sparkline7d?                            = Sparkline7d(),

)