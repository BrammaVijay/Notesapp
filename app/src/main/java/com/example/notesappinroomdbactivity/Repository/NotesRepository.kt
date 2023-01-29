package com.example.notesappinroomdbactivity.Repository

import androidx.lifecycle.LiveData
import com.example.notesappinroomdbactivity.Database.NotesDao
import com.example.notesappinroomdbactivity.Models.Notes

class NotesRepository(private val notesDao: NotesDao) {

    val readData:LiveData<List<Notes>> = notesDao.readData()

    suspend fun deleteData(notes: Notes)=notesDao.deleteData(notes)

    suspend fun insertData(notes: Notes)=notesDao.insertData(notes)

    suspend fun updateData(notes: Notes)=notesDao.updateData(notes)

}