package com.example.myfirstcomposeapp

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GymRepository {
    private val apiService : GymsApiService = Retrofit.Builder()
        .addConverterFactory(
            GsonConverterFactory.create()
        )
        .baseUrl( "https://gyms-f7466-default-rtdb.firebaseio.com/"
        )
        .build()
        .create(GymsApiService::class.java)
    private var gymDao = GymsDatabase.getDaoInstance(
        GymsApplication.getApplicationContext())

     suspend fun loadGyms()= withContext(Dispatchers.IO){
        try {
            updateLocalDataBase()
        }
        catch (ex:Exception){
            if(gymDao.getAll().isEmpty()){
                throw Exception("something Went Wrong")
            }
        }
    }

    suspend fun getGyms():List<Gym>{
        return withContext(Dispatchers.IO){
            return@withContext gymDao.getAll()
        }
    }

    suspend fun updateLocalDataBase() {
        val gyms = apiService.getGyms()
        val favouriteGymsList = gymDao.getFavouriteGyms()

        gymDao.addAll(gyms)

        gymDao.updateAll(
            favouriteGymsList.map{GymFavouriteState(id = it.id,true)}
        )
    }

     suspend fun toggleFavouriteGym(gymId: Int, state: Boolean)= withContext(Dispatchers.IO){
        gymDao.update(
            GymFavouriteState(
                id = gymId
            )
        )
        gymDao.getAll()
    }
}