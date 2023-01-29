package com.example.notesappinroomdbactivity.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.notesappinroomdbactivity.Database.NotesDataBase
import com.example.notesappinroomdbactivity.Models.Notes
import com.example.notesappinroomdbactivity.Repository.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesViewModel(application: Application):AndroidViewModel(application) {

    var notesLiveData:LiveData<List<Notes>>

    private var notesRepository:NotesRepository

    val liveSearch: MutableLiveData<String> by lazy { MutableLiveData<String>() }

    init {

        val userDao=NotesDataBase.getDatabase(application).notesDao()

        notesRepository= NotesRepository(userDao)

        notesLiveData=notesRepository.readData

    }

    fun setMessage(searVal:String){

        viewModelScope.launch (Dispatchers.Main){

          liveSearch.value=searVal

        }
    }

    fun insertData(notes: Notes){

        viewModelScope.launch (Dispatchers.IO){

            notesRepository.insertData(notes)
        }
    }

    fun deleteData(notes: Notes){

        viewModelScope.launch (Dispatchers.IO){

            notesRepository.deleteData(notes)

        }
    }

    fun updateData(notes: Notes){

        viewModelScope.launch (Dispatchers.IO){

            notesRepository.updateData(notes)
        }
    }
}