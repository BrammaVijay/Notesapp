package com.example.notesappinroomdbactivity.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.notesappinroomdbactivity.Models.Notes
import com.example.notesappinroomdbactivity.databinding.ActivityAddNotesBinding
import com.example.notesappinroomdbactivity.viewModel.NotesViewModel
import java.text.SimpleDateFormat
import java.util.*

class AddNotesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNotesBinding


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding=ActivityAddNotesBinding.inflate(layoutInflater)

        setContentView(binding.root)

        passingData()

        backPressed()
    }

    private fun passingData(){

        binding.addItemClick.setOnClickListener {

            val intent = Intent(this, MainActivity::class.java)

            val addingTitle=binding.updateTittle.text

            val des=binding.description.text

            if (addingTitle.toString().isEmpty() || des.toString().isEmpty()){

                Toast.makeText(this, "Failure", Toast.LENGTH_SHORT).show()

            }

            else{

                intent.putExtra("titlePass",addingTitle.toString())

                intent.putExtra("description",des.toString())

                setResult(RESULT_OK,intent)

                finish()
            }
        }
    }

    private fun backPressed(){

        binding.backArrow.setOnClickListener {

            onBackPressed()
        }
    }

}