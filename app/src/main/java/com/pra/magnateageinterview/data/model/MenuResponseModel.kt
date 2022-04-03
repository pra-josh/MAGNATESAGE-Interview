package com.pra.magnateageinterview.data.model


import com.google.gson.annotations.SerializedName

data class MenuResponseModel(
    @SerializedName("data")
    var `data`: List<Data>
)