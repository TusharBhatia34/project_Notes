package com.example.project2_notesapp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
sealed interface Dao{


    @Delete
   suspend fun deleteName(databaseTable: Table)

    @Upsert
   suspend fun addName(databaseTable: Table)

    @Query("SELECT * FROM contact")
   fun getAllItems(): Flow<List<Table>>
}