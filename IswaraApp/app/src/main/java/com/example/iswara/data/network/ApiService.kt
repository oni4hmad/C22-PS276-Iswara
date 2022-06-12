package com.example.iswara.data.network

import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    /* All story base url */
    @GET("cerita")
    fun getListCerita(
        @Query("page") page: Int,
        @Query("limit") limit: Int = 5,
        @Query("sortBy") sortBy: String = "id",
        @Query("order") order: String = "desc"
    ): Call<List<Cerita>>

    /* All story base url */
    @GET("cerita/{id}/tanggapan")
    fun getListTanggapan(
        @Path("id") idCerita: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int = 5,
        @Query("sortBy") sortBy: String = "date",
        @Query("order") order: String = "asc"
    ): Call<List<Tanggapan>>

    /* User story base url */
    @GET("user/{id}/cerita")
    fun getListUserCerita(
        @Path("id") idUser: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int = 5,
        @Query("sortBy") sortBy: String = "date",
        @Query("order") order: String = "desc"
    ): Call<List<Cerita>>

    /* User story base url : login */
    @GET("user")
    fun getListUser(
        @Query("filter") email: String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 1
    ): Call<List<User>>

    /* User story base url : register */
    @FormUrlEncoded
    @POST("user")
    fun postUser(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("phoneNum") phoneNum: String
    ): Call<User>

    /* All story base url : post story */
    @FormUrlEncoded
    @POST("cerita")
    fun postStory(
        @Field("name") name: String,
        @Field("date") date: String,
        @Field("cerita") cerita: String
    ): Call<Cerita>

    /* User story base url : post story */
    @FormUrlEncoded
    @POST("user/{uid}/cerita")
    fun postStoryWithUserId(
        @Field("name") name: String,
        @Field("userId") userId: String,
        @Field("date") date: String,
        @Field("cerita") cerita: String,
        @Path("uid") uid: String = userId,
    ): Call<Cerita>

    /* All story base url : post tanggapan */
    @FormUrlEncoded
    @POST("cerita/{ceritaId}/tanggapan")
    fun postTanggapan(
        @Field("name") name: String,
        @Field("date") date: String,
        @Field("tanggapan") tanggapan: String,
        @Path("ceritaId") ceritaId: String
    ): Call<Tanggapan>

}