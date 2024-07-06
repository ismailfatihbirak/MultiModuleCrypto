package com.example.multimodulecrypto.core.model

import com.google.gson.annotations.SerializedName


data class Sparkline7d (

  @SerializedName("price" ) var price : ArrayList<Double> = arrayListOf()

)