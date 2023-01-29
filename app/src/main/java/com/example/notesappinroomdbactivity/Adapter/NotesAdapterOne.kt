package com.example.notesappinroomdbactivity.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.notesappinroomdbactivity.Models.Notes
import com.example.notesappinroomdbactivity.databinding.ListItemBinding

class NotesAdapterOne:RecyclerView.Adapter<NotesAdapterOne.ViewHolderOne>() {

    inner class ViewHolderOne(private val binding: ListItemBinding):RecyclerView.ViewHolder(binding.root){

        fun bindItems(items: Notes){

            binding.tvTitle.text=items.title

            binding.tvNotes.text=items.note

            binding.tvDate.text=items.date
        }
    }

    private var differCallback=object:DiffUtil.ItemCallback<Notes>(){

        override fun areItemsTheSame(oldItem: Notes, newItem: Notes): Boolean {

            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: Notes, newItem: Notes): Boolean {

            return oldItem==newItem
        }

    }

    val differ=AsyncListDiffer(this,differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderOne {

        val inflate=LayoutInflater.from(parent.context)

        val binding=ListItemBinding.inflate(inflate,parent,false)

        return ViewHolderOne(binding)

    }

    override fun onBindViewHolder(holder: ViewHolderOne, position: Int) {

        holder.bindItems(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}