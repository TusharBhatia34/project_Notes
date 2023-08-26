package com.example.project2_notesapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ContentScreen(state: State , onEvent:(Event)->Unit,navController: NavController) {

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {
        Row(modifier = Modifier.fillMaxWidth()) {

            Box(modifier = Modifier.weight(1f)){

                IconButton(onClick = {  focusManager.clearFocus()
                    keyboardController?.hide()
                    navController.navigate("listScreen"){
                        popUpTo("listScreen"){inclusive = true}
                    } }
                    , modifier = Modifier
                        .background(Color.Transparent)
                        .size(56.dp)) {
                    Icon(imageVector = Icons.Filled.ArrowBack , contentDescription = null )
                }
            }

            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterEnd){

                IconButton(onClick = {
                    focusManager.clearFocus()
                    keyboardController?.hide()     }
                    , modifier = Modifier
                        .background(Color.Transparent)
                        .size(56.dp)) {
                    Icon(imageVector = Icons.Filled.Done , contentDescription = null )
                }
            }
        }
        val customTextSelectionColors = TextSelectionColors(
            handleColor = Color.Black,
            backgroundColor = Color.LightGray
        )
        CompositionLocalProvider(  LocalTextSelectionColors provides customTextSelectionColors) {
            TextField(modifier = Modifier.fillMaxWidth(),
                value = state.title,
                onValueChange = { onEvent(Event.updateContent(state.id,it,state.content))},

                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    focusedIndicatorColor =  Color.Transparent, //hide the indicator
                    unfocusedIndicatorColor =  Color.Transparent, //hide the indicator
                    cursorColor = Color.Black,

                    ),
                placeholder = { Text(text = "Title")}
            )
        }
        Box(modifier = Modifier
            .fillMaxSize()
            .weight(1f), contentAlignment = Alignment.TopStart){
            CompositionLocalProvider(  LocalTextSelectionColors provides customTextSelectionColors) {

                TextField(modifier = Modifier.fillMaxWidth(),
                    value = state.content,
                    onValueChange = {onEvent(Event.updateContent(state.id,state.title,it)) },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.White,
                        focusedIndicatorColor =  Color.Transparent, //hide the indicator
                        unfocusedIndicatorColor =  Color.Transparent, //hide the indicator
                        cursorColor = Color.Black,

                        ),
                    placeholder = { Text(text = "Start Typing")}
                )
            }
        }





    }
}