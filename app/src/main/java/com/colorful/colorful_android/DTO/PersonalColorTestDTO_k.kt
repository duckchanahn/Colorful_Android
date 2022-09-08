package com.colorful.colorful_android.DTO

import com.google.gson.annotations.SerializedName

//data class PersonalColorTestDTO_k(val customerId : Int, val binary : ByteArray) {
data class PersonalColorTestDTO_k(
    @SerializedName("customerId")
    val customerId : Int,
    @SerializedName("binary")
    val binary : ByteArray
)