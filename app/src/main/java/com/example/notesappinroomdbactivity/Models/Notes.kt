package com.example.notesappinroomdbactivity.Models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "notes_tables")
data class Notes(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val title:String,
    val note:String,
    val date:String

):Serializable
