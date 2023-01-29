package com.example.notesappinroomdbactivity.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notesappinroomdbactivity.Adapter.NotesAdapter
import com.example.notesappinroomdbactivity.Models.Notes
import com.example.notesappinroomdbactivity.R
import com.example.notesappinroomdbactivity.databinding.ActivityMainBinding
import com.example.notesappinroomdbactivity.viewModel.NotesViewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var staggeredLayoutManager: StaggeredGridLayoutManager

    private lateinit var notesAdapter: NotesAdapter

    private val viewModel: NotesViewModel by lazy { ViewModelProvider(this).get(NotesViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setAdapter()

        getPassData()

        observeData()

        update()

        deleteData()

        deleteVal()

        searchListenText()

        searchData()

    }

    private fun update(){

        notesAdapter.updateData {

            val intent=Intent(this,UpdateActivity::class.java)

            intent.putExtra("data",it)

            startActivity(intent)

        }
    }

    private fun observeData(){

        viewModel.notesLiveData.observe(this, androidx.lifecycle.Observer { response->

            notesAdapter.differ.submitList(response)
        })
    }

    private fun searchData() {

        viewModel.liveSearch.observe(this, androidx.lifecycle.Observer {response->

            if (response.toString().isNotEmpty()) {

                filterArray(response.toString())

            }
            else {

                viewModel.notesLiveData.observe(this, androidx.lifecycle.Observer {

                    notesAdapter.differ.submitList(it)

                })
            }
        })
    }

    private fun filterArray(search: String) {

        val list = ArrayList<Notes>()

         list.clear()

        viewModel.notesLiveData.observe(this, androidx.lifecycle.Observer {response->

            if (search.isNotEmpty()) {

                for (item in response) {

                    if (item.title.lowercase().trim().contains(search.lowercase() ) || item.note.lowercase().trim().contains(search.lowercase()) ) {

                        list.add(item)
                    }
                }
            }

            if (list.isEmpty()) {

                notesAdapter.differ.submitList(list)

            } else {

                notesAdapter.differ.submitList(list)
            }
        })
    }

    private fun searchListenText() {

        binding.searchView.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                try {

                    viewModel.setMessage(s.toString())

                }
                catch (e:Exception){

                    Toast.makeText(this@MainActivity, e.toString(), Toast.LENGTH_SHORT).show()

                }

            }
            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

    private fun deleteData(){

        notesAdapter.deleteItem { notes, cardView ->

            val menuButton=PopupMenu(this,cardView)

            menuButton.menuInflater.inflate(R.menu.pop_up_menu,menuButton.menu)

            menuButton.setOnMenuItemClickListener {

                when(it.itemId){

                    R.id.deleteRelative->{

                        viewModel.deleteData(notes)
                    }

                    else->{

                    }
                }
               true
            }

            menuButton.show()
        }
    }

    private fun deleteVal(){

        val itemTouchHelper=object :ItemTouchHelper.SimpleCallback(

            ItemTouchHelper.UP or ItemTouchHelper.DOWN,

            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ){
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,  target: RecyclerView.ViewHolder )=true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                val position=viewHolder.adapterPosition

                viewModel.deleteData(notesAdapter.differ.currentList[position])

            }
        }

        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.recyclerView)
    }

    private fun getPassData(){

        val getLauncher=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result->

            if (result.resultCode== RESULT_OK){

                val intent=result.data

                val title=intent?.getStringExtra("titlePass")

                val desc=intent?.getStringExtra("description")

                val notes=Notes(

                    0,

                    title.toString(),

                    desc.toString(),

                    dateFormat()
                )

                viewModel.insertData(notes)
            }
        }

        binding.addButton.setOnClickListener {

            val intent=Intent(this,AddNotesActivity::class.java)

            getLauncher.launch(intent)
        }
    }

    private fun dateFormat():String{

        val simple=SimpleDateFormat("dd-MM-yyyy HH:mm:ss z")

        return simple.format(Date())
    }

    private fun setAdapter(){

        notesAdapter= NotesAdapter()

        binding.recyclerView.adapter=notesAdapter
    }

}