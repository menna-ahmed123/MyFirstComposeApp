package com.example.myfirstcomposeapp

import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
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

class GymsViewModel()
    : ViewModel()  {

     private var _state by // data inside composable
        mutableStateOf(GymsScreenState(
            gyms = emptyList(),
            isLoading = true
        )
        )
    val state: State<GymsScreenState>
        get() = derivedStateOf { _state }

    private val repo = GymRepository()
    private val errorHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
        _state = _state.copy(
            isLoading = false,
            error = throwable.message
        )
    }

    init {
        getGyms()

    }

      private fun getGyms() {
          viewModelScope.launch(errorHandler) {
              val receivedGyms =repo.getAllGyms()
                  _state = _state.copy(
                      gyms = receivedGyms,
                      isLoading = false,
                  )
          }

      }


    fun toggleFavoriteState(gymId:Int){
        val gyms = _state.gyms.toMutableList() // عشان اقدر اعدل ع الstate
        val ItemIndex =gyms.indexOfFirst { it.id==gymId }

        viewModelScope.launch {
            val updatedGymsList=repo.toggleFavouriteGym(gymId,!gyms[ItemIndex].isFavorite)
            _state = _state.copy(gyms = updatedGymsList)
        }
    }

}