package com.example.project2_notesapp

sealed interface Event{
    data class DeleteName(val table: Table) :Event
   object AddName :Event
    object HideDialog:Event
    object ShowDialog:Event
    data class SetName( val name:String):Event
    data class SetContent( val title:String,val content:String,val id:Int):Event
   data class GetDeleteTopbar(val title:String):Event
    object GetAddTopbar:Event
    data class updateContent(val id:Int,val title: String,val content: String):Event
}