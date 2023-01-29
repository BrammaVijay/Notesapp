package com.example.notesappinroomdbactivity.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.notesappinroomdbactivity.Models.Notes
import com.example.notesappinroomdbactivity.R
import com.example.notesappinroomdbactivity.databinding.ActivityUpdateBinding
import com.example.notesappinroomdbactivity.viewModel.NotesViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*



class UpdateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateBinding

    private val viewModel: NotesViewModel by lazy { ViewModelProvider(this).get(NotesViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding=ActivityUpdateBinding.inflate(layoutInflater)

        setContentView(binding.root)

        updateFunction()
    }

    private fun updateFunction(){

        val bundle=intent.extras

        val data=bundle?.getSerializable("data") as Notes

        Toast.makeText(this, data.id.toString(), Toast.LENGTH_SHORT).show()

       binding.updateTittle.setText(data.title)

        binding.updateDescription.setText(data.note)

        binding.addItemClick.setOnClickListener {

            if (binding.updateTittle.text.toString().isEmpty() || binding.updateDescription.text.toString().isEmpty()){

                Toast.makeText(this, "Failure", Toast.LENGTH_SHORT).show()

            }

            else{

                val notes= Notes(

                    data.id,

                    binding.updateTittle.text.toString(),

                    binding.updateDescription.text.toString(),

                    dateFormat()
                )

                viewModel.updateData(notes)

                val intent= Intent(this,MainActivity::class.java)

                startActivity(intent)

                finish()
            }
        }
    }

    private fun dateFormat():String{

        val simple= SimpleDateFormat("dd-MM-yyyy HH:mm:ss z")

        return   simple.format(Date())

    }
}