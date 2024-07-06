package com.example.multimodulecrypto.core.model

import com.google.gson.annotations.SerializedName


data class ReposUrl (

  @SerializedName("github"    ) var github    : ArrayList<String> = arrayListOf(),
  @SerializedName("bitbucket" ) var bitbucket : ArrayList<String> = arrayListOf()

)