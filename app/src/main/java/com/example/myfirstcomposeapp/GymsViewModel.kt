package com.example.myfirstcomposeapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GymsViewModel(
    private val stateHandle:SavedStateHandle)
    : ViewModel()  {

    var state by // data inside composable
        mutableStateOf(
           emptyList<Gym>()
        )
    private var apiService : GymsApiService
    private var gymDao = GymsDatabase.getDaoInstance(GymsApplication.getApplicationContext())
    private val errorHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }

    init {
        val retrofit :Retrofit =Retrofit.Builder()
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .baseUrl( "https://gyms-f7466-default-rtdb.firebaseio.com/"
            )
            .build()
        apiService = retrofit.create(GymsApiService::class.java)
        getGyms()
    }

      private fun getGyms() {
          viewModelScope.launch(errorHandler) {
              val gyms = getGymsFromDB()
                  state = gyms.restoreSelectedGyms()
          }

      }
    private suspend fun getGymsFromDB()= withContext(Dispatchers.IO){
        try {
            val gyms = apiService.getGyms()
            gymDao.addAll(gyms)
            return@withContext gyms
        }
        catch (ex:Exception){
             gymDao.getAll()
        }


    }

    fun toggleFavoriteState(gymId:Int){
        val gyms = state.toMutableList() // عشان اقدر اعدل ع الstate
        val ItemIndex =gyms.indexOfFirst { it.id==gymId }
        gyms[ItemIndex] = gyms[ItemIndex].copy(isFavorite =!gyms[ItemIndex].isFavorite)
        state=gyms
    }

    private fun storeSelectedGym(gym:Gym){
        val savedHandleList =stateHandle.get<List<Int>?>(Fav_Ids).orEmpty().toMutableList()
        if (gym.isFavorite) savedHandleList.add(gym.id)
        else savedHandleList.remove(gym.id)
        stateHandle[Fav_Ids] =savedHandleList
    }
    private fun   List<Gym>.restoreSelectedGyms():List <Gym>{
        stateHandle.get<List<Int>?>(Fav_Ids)?.let { savedIds->
            savedIds.forEach { gymId->
                this.find { it.id==gymId }?.isFavorite = true

            }
        }
        return this
    }
    companion object{
        const val Fav_Ids ="FavoriteItem"
    }

}