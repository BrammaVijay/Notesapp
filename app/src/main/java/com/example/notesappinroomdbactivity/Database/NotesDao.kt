package com.example.notesappinroomdbactivity.Database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.notesappinroomdbactivity.Models.Notes
@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(notes: Notes)

    @Query("SELECT*FROM notes_tables ORDER BY id ASC")
    fun readData():LiveData<List<Notes>>

    @Delete
    suspend fun deleteData(notes: Notes)

    @Update
    suspend fun updateData(notes: Notes)

}