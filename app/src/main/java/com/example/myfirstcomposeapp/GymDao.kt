package com.example.myfirstcomposeapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface GymDao {
    @Query("SELECT * FROM gyms")
    suspend fun getAll(): List<Gym>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAll(gym : List<Gym>)

    @Update(entity = Gym::class)
    suspend fun update(gymFavouriteState: GymFavouriteState)
    @Query("SELECT * FROM gyms WHERE is_favourite = 1" )
    suspend fun getFavouriteGyms(): List<Gym>
    @Update(entity = Gym::class)
    suspend fun updateAll(gymsStates : List<GymFavouriteState>)
}