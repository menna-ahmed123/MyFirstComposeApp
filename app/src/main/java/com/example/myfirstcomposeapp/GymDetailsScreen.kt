package com.example.myfirstcomposeapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable

fun GymDetailsScreen(){
    val viewModel :GymDetailsViewModel =  viewModel()

    val item = viewModel.state.value
    
    item?.let { 
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)) {
            DefaultIcon(icon =Icons.Filled.Place
                , modifier =Modifier.padding(bottom = 32.dp, top = 32.dp) ,
                contentDiscription = "Location Icon" )
            GymDetails(gym = it,
                modifier =Modifier.padding(bottom = 32.dp),
                horizontal = Alignment.CenterHorizontally)
        }
    }
}

