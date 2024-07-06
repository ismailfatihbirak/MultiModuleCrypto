package com.example.multimodulecrypto.core.model

import com.google.gson.annotations.SerializedName


data class SparklineIn7d (

  @SerializedName("price" ) var price : ArrayList<Double> = arrayListOf()

)