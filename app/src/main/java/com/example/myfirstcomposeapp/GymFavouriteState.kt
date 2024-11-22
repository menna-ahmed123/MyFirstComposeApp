package com.example.myfirstcomposeapp

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class GymFavouriteState(
    @ColumnInfo(name= "gym_id")
    val id: Int,
    @ColumnInfo (name="is_favourite")
    val isFavorite: Boolean = false
)
