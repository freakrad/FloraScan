package com.example.florascan.helper

import com.example.florascan.response.DeleteResponse
import com.example.florascan.response.LoginResponse
import com.example.florascan.response.RegisterResponse
import okhttp3.MultipartBody
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiServiceMl {

    @Multipart
    @POST("prediction")
    suspend fun uploadImage(
        @Part image: MultipartBody.Part,
        @Header("Authorization") token: String
    ): ResponseMl

    @GET("prediction")
    suspend fun getHistory(@Header("Authorization") token: String): HistoryResponse

    @DELETE("prediction")
    suspend fun deleteHistory(
        @Header("Authorization") token: String,
        @Query("predictionId") id: Int
    ): DeleteResponse

    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("email") email: String,
        @Field("username") name: String,
        @Field("password") password: String
    ): RegisterResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("emailOrUsername") emailOrUsername: String,
        @Field("password") password: String
    ): LoginResponse
}