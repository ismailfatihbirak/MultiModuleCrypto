package com.example.multimodulecrypto.core.model

import com.google.gson.annotations.SerializedName


data class RootId (

  @SerializedName("id"                              ) var id                           : String?           = null,
  @SerializedName("symbol"                          ) var symbol                       : String?           = null,
  @SerializedName("name"                            ) var name                         : String?           = null,
  @SerializedName("web_slug"                        ) var webSlug                      : String?           = null,
  @SerializedName("asset_platform_id"               ) var assetPlatformId              : String?           = null,
  @SerializedName("block_time_in_minutes"           ) var blockTimeInMinutes           : Int?              = null,
  @SerializedName("hashing_algorithm"               ) var hashingAlgorithm             : String?           = null,
  @SerializedName("description"                     ) var description                  : Description?      = Description(),
  @SerializedName("image"                           ) var image                        : Image?            = Image(),
  @SerializedName("genesis_date"                    ) var genesisDate                  : String?           = null,
  @SerializedName("sentiment_votes_up_percentage"   ) var sentimentVotesUpPercentage   : Double?           = null,
  @SerializedName("sentiment_votes_down_percentage" ) var sentimentVotesDownPercentage : Double?           = null,
  @SerializedName("watchlist_portfolio_users"       ) var watchlistPortfolioUsers      : Int?              = null,
  @SerializedName("market_cap_rank"                 ) var marketCapRank                : Int?              = null,
  @SerializedName("market_data"                     ) var marketData                   : MarketData?       = MarketData(),
  @SerializedName("status_updates"                  ) var statusUpdates                : ArrayList<String> = arrayListOf(),
  @SerializedName("last_updated"                    ) var lastUpdated                  : String?           = null

)