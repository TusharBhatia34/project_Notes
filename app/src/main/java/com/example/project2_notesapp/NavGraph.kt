package com.example.project2_notesapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavGraph(navController: NavHostController,state: State,onEvent:(Event)->Unit) {

    NavHost(navController = navController , startDestination = "listScreen" ){
        composable(route = "listScreen" ){
            Toolbar(navController,state,onEvent)
        }
        composable(route = "contentScreen" ){
           ContentScreen(state,onEvent,navController)
        }


    }
}