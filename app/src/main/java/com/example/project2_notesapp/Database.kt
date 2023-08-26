package com.example.project2_notesapp

import androidx.room.RoomDatabase
import androidx.room.Database


@Database(
entities = [Table::class],
    version = 1
)
   abstract class Database:RoomDatabase() {
       abstract val dao:Dao
}