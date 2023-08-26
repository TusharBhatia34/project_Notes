package com.example.project2_notesapp

data class State(
    val list_name:List<Table> = emptyList(),  //list of blocks
    val name:String = "",   //for textField
    val isAddingName:Boolean = false,// for dialog box
    val isDeletingName:Boolean = false , //for topBar to change its state to have delete option
    val id:Int = 0,     //id to send from list screen to content screen
    val content:String = "",
    val title:String = ""
)
