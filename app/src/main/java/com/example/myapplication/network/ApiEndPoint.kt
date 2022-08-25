package com.example.myapplication.network

import com.example.myapplication.model.GetUserReponse
import com.example.myapplication.model.UserResponseModel
import com.example.myapplication.model.VerifictionNumberModel
import retrofit2.Call
import retrofit2.http.*

interface ApiEndPoint {

    @FormUrlEncoded
    @POST("users")
    fun getUser(
        @Field("phoneNumber") phone_number: String?
    ): Call<GetUserReponse>?


    @GET("verification-numbers")
    fun getVerificationNumbers(
    ): Call<VerifictionNumberModel>?


    @PATCH("users/{phoneNumber}")
    fun setUserStatus(
        @Path("phoneNumber") phone_number: String?
    ): Call<GetUserReponse>?
}