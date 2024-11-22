package com.example.myfirstcomposeapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName



@Entity (tableName = "gyms")
data class Gym(
    @PrimaryKey
    @ColumnInfo (name="gym_id")
    val id: Int,
    @ColumnInfo (name="gym_name")
    @SerializedName("gym_name")
    val name: String,
    @SerializedName("gym_location")
    val place: String,
    @ColumnInfo (name="is_favourite")
    var isFavorite: Boolean = false
)