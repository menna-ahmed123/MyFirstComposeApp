package com.example.myfirstcomposeapp

class ToggleFavouriteStateUseCase {
    private val gymRepository = GymRepository()
    private val getSortedGymsUseCase = GetSortedGymsUseCase()

    suspend operator fun invoke(id:Int, oldState:Boolean):List<Gym>{
        val newState =oldState.not()
         gymRepository.toggleFavouriteGym(id,newState)
        return getSortedGymsUseCase()
    }
}