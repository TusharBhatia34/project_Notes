package com.example.project2_notesapp

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Toolbar(
    navController: NavController,
    state: State,
    onEvent:(Event)-> Unit
) {
    Scaffold(
        topBar ={
            TopAppBar(
                    navigationIcon = {
                        if (state.isDeletingName){
                            IconButton(onClick = { onEvent(Event.GetAddTopbar)  }) {
                                Icon(
                                    imageVector = Icons.Filled.Clear,
                                    contentDescription = "clear",
                                    tint = Color.White,
                                    modifier = Modifier.size(32.dp)
                                )
                            }
                        }

                    } ,
                title = {
                   if(!state.isDeletingName){
                        Text(text = "  Notes"
                            , style = TextStyle(color = Color.White,fontSize = 25.sp)

                        )
                    }


                },
                 actions = {
                     if (state.isDeletingName){
                         state.list_name.forEach {  section->
                                    if (section.name == state.title){
                                        IconButton(onClick = { onEvent(Event.DeleteName(section)) }  )
                                        {
                                            Icon(
                                                imageVector = Icons.Filled.Delete,
                                                contentDescription = "Delete",
                                                tint = Color.White,
                                                modifier = Modifier.size(32.dp)
                                            )
                                        }
                                    }


                         }
                     }
                     else{
                         IconButton(onClick = {  onEvent(Event.ShowDialog)  }) {
                             Icon(
                                 imageVector = Icons.Filled.Add,
                                 contentDescription = "add",
                                 tint = Color.White,
                                 modifier = Modifier.size(32.dp)
                             )
                         }
                     }

                }, colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color.Black
                )

            )
        }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)) {

            if (state.isAddingName){
            CustomDialog (state,onEvent)
        }

            ListOfSections(navController,state ,onEvent,
                Modifier
                    .background(color = Color.White)
                    .padding(12.dp)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(5.dp))
                    .background(Color.LightGray)

            )

             }

    }


}
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListOfSections(navController: NavController,state:State,onEvent:(Event)-> Unit ,modifier: Modifier = Modifier ){

 LazyVerticalGrid(columns = GridCells.Fixed(2), content = {
     items(state.list_name){section->
             Box(
                 modifier = modifier.combinedClickable(
                     onClick = {
                                onEvent(Event.SetContent(section.name,section.content,section.id))
                                navController.navigate("contentScreen")
                     },
                     onLongClick = {onEvent(Event.GetDeleteTopbar(section.name))}
                 ),
                 contentAlignment = Alignment.Center
             ) {
                 Text(text = section.name )
             }
         }


 })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDialog(
    state: State,
    onEvent: (Event) -> Unit
) {
    Dialog(onDismissRequest = { onEvent(Event.HideDialog)}) {


        Card(
            shape = RoundedCornerShape(15.dp)
            , modifier = Modifier
                .size(300.dp, 270.dp)
                .padding(8.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            )
            , colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {

            Column(
                modifier = Modifier
                    .background(Color.Transparent)
                , horizontalAlignment = Alignment.CenterHorizontally
                , verticalArrangement = Arrangement.Top
            ) {


                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp), color = Color.White
                )

                Text(
                    text = "Hello!",

                )

                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(30.dp), color = Color.White
                )

                OutlinedTextField(
                    value = state.name
                    , onValueChange ={onEvent(Event.SetName(it)) }
                    , modifier = Modifier
                        .width(210.dp)
                    , label = { Text(text = "Name")
                    },shape = RoundedCornerShape(15.dp)
                    ,colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Black
                        , unfocusedBorderColor = Color.Black
                        ,cursorColor = Color.Black
                        , focusedLabelColor = Color.Black
                    )
                    , singleLine = true
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                    , verticalArrangement = Arrangement.Center
                    , horizontalAlignment = Alignment.CenterHorizontally
                )
                {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                        , verticalAlignment = Alignment.CenterVertically
                        , horizontalArrangement = Arrangement.SpaceEvenly
                    ) {

                        Button(
                            onClick = { onEvent(Event.AddName)}
                            , colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White
                            )
                            , shape = RoundedCornerShape(15.dp)
                            , border = BorderStroke(1.dp, Color.Black)
                        ) {
                            Text(
                                text = "Add"
                                , color = Color.Black
                            )
                        }

                        Button(onClick = { onEvent(Event.HideDialog) }
                            , colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Black
                            )
                            , shape = RoundedCornerShape(15.dp)
                        )  {
                            Text(text = "Cancel"
                                ,color =Color.White
                            )
                        }
                    }

                }
            }
        }
    }
}


