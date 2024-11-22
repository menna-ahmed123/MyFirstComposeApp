package com.example.myfirstcomposeapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun GymScreen(onItemClick: (Int) -> Unit){

    val vm: GymsViewModel = viewModel( )   // فيو مودل بتعبر عن ال this
    LazyColumn() { //like recuclar view
        items(vm.state){ gym ->
            GymItem(
                gym = gym,

                onClick = {vm.toggleFavoriteState(it)},
                onItemClick = {id-> onItemClick(id) }
            )

        }
    }

}

@Composable
fun GymItem(gym: Gym,    onClick:(Int)->Unit,onItemClick:(Int)-> Unit
) {
    var isFavouriteState by remember { mutableStateOf(false) }
    val icon = if (gym.isFavorite) {
        Icons.Filled.Favorite
    } else {
        Icons.Filled.FavoriteBorder
    }
    Card(
        modifier = Modifier.padding(8.dp)
            .clickable { onItemClick(gym.id) }
    ) {
        Row (  Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
             ){
            DefaultIcon(Icons.Filled.Place,Modifier.weight(0.15f),
                contentDiscription = "Location Icon")
            GymDetails(gym,Modifier.weight(0.70f))
            DefaultIcon(icon,Modifier.weight(0.15f), contentDiscription = "Location Icon"){
                onClick(gym.id)
            }
        }

    }
}

@Composable
fun DefaultIcon(
    icon : ImageVector
    ,modifier: Modifier,
    contentDiscription:String,
    onClick:()->Unit={}
) {

    Image(
        imageVector = icon,
        contentDescription = "Favorite Icon",
        modifier=modifier.clickable {

        },
        colorFilter = ColorFilter.tint(Color.DarkGray)


    )


}

@Composable
fun GymDetails( gym: Gym,modifier: Modifier, horizontal: Alignment.Horizontal = Alignment.Start) {

    Column(modifier = Modifier, horizontalAlignment = horizontal) {
        Text(
            text =gym.name,
            color = Color.Blue,
             // Use a predefined style
        )

        Text(
            text = gym.place,
             // Use a predefined style
        )
    }
    }

//@Preview(showSystemUi = true)
//@Composable

//fun GymScreenPreview(){
    //GymScreen()
//}