package com.example.myfirstcomposeapp

import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

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

    private val useCase = GetInitialGymsUseCase()
    private val toggleFavouriteStateUseCase =ToggleFavouriteStateUseCase()
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
              val receivedGyms =useCase()// call instance as function
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
            val updatedGymsList= toggleFavouriteStateUseCase(gymId, gyms[ItemIndex].isFavorite)
            _state = _state.copy(gyms = updatedGymsList)
        }
    }

}