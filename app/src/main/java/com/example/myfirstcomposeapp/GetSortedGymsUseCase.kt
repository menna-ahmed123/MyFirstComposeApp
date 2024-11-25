package com.example.myfirstcomposeapp

class GetSortedGymsUseCase {
    private val gymsRepository = GymRepository()
    suspend operator fun invoke():List<Gym>{
        return gymsRepository.getGyms().sortedBy { it.name }
    }
}