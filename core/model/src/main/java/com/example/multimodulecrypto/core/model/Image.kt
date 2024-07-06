package com.example.multimodulecrypto.core.model

import com.google.gson.annotations.SerializedName


data class Image (

  @SerializedName("thumb" ) var thumb : String? = null,
  @SerializedName("small" ) var small : String? = null,
  @SerializedName("large" ) var large : String? = null

)