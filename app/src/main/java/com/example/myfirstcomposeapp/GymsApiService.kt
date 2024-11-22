package com.example.myfirstcomposeapp

import retrofit2.http.GET
import retrofit2.http.Query

interface GymsApiService {
    @GET("gyms.json") // retrofit convert this function to http call
    suspend fun getGyms(): List<Gym>

    @GET("gyms.json?orderBy=\"id\"")
    suspend fun getGym(
        @Query("equalTo") id:Int
    ):Map<String,Gym>
}