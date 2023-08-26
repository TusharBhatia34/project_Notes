package com.example.project2_notesapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contact")
data class Table(

val name:String, // name of every block
val content:String, // content inside the block

@PrimaryKey(autoGenerate = true)
val id:Int=0
)
