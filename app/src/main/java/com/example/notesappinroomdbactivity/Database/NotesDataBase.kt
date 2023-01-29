package com.example.notesappinroomdbactivity.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notesappinroomdbactivity.Models.Notes

@Database(entities = [Notes::class], version = 1, exportSchema = false)
abstract class NotesDataBase:RoomDatabase() {

    abstract fun notesDao():NotesDao

    companion object{
        @Volatile
        var INSTANCE:NotesDataBase?=null

        fun getDatabase(context: Context):NotesDataBase{
            val tempInstance= INSTANCE
            if (tempInstance!=null)
            {
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext,NotesDataBase::class.java,
                    "notes_tables"
                ).build()
                INSTANCE=instance
                return instance
            }
        }
    }
}