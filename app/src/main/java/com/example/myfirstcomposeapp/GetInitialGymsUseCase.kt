package com.example.myfirstcomposeapp

class GetInitialGymsUseCase {
    private val gymRepository = GymRepository()
    private val getSortedGymsUseCase = GetSortedGymsUseCase()

    suspend operator fun invoke():List<Gym>{ // operator هنقدر نعمل instance من الكلاس ونستخدم  الفانكشن ع طول
        gymRepository.loadGyms()
        return getSortedGymsUseCase()
    }
}