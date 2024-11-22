package com.example.myfirstcomposeapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Gym::class],
    version = 1,
    exportSchema = false
)
 abstract class GymsDatabase : RoomDatabase() { //implement in background

     abstract val dao:GymDao // بربط الداتا اكسسيس اوبجكت

     companion object{
         @Volatile
         private var daoInstance :GymDao? =null

         private fun buildDatabase(context: Context): GymsDatabase =
             Room.databaseBuilder(
                 context.applicationContext,
                 GymsDatabase::class.java,
                 "gym_database"
             ).fallbackToDestructiveMigration()
                 .build()

         fun getDaoInstance(context: Context) : GymDao{
             synchronized(this){
                 if(daoInstance == null){
                     daoInstance = buildDatabase(context).dao
                 }
                 return daoInstance as GymDao
             }
         }
     }
}