package com.pra.magnateageinterview.data.model


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("data")
    var `data`: List<String>,
    @SerializedName("end_date")
    var endDate: String,
    @SerializedName("end_time")
    var endTime: String,
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("start_date")
    var startDate: String,
    @SerializedName("start_time")
    var startTime: String,
    @SerializedName("time_interval")
    var timeInterval: Int
)