package com.example.notesappinroomdbactivity.Adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.notesappinroomdbactivity.Models.Notes
import com.example.notesappinroomdbactivity.R
import com.example.notesappinroomdbactivity.databinding.ListItemBinding
import kotlin.random.Random

class NotesAdapter:RecyclerView.Adapter<NotesAdapter.ViewHolderOne>() {

    private var listener:((Notes)->Unit)?=null

    fun updateData(clickEvent:(Notes)->Unit){

        listener=clickEvent
    }

    private var listenerEvent:((Notes,CardView)->Unit)?=null

    fun deleteItem(clickListen:(Notes,CardView)->Unit){

        listenerEvent=clickListen
    }

    inner class ViewHolderOne(private val binding: ListItemBinding):RecyclerView.ViewHolder(binding.root){

        fun bindItems(items:Notes){

            binding.tvTitle.text=items.title

            binding.tvNotes.text=items.note

            binding.tvDate.text=items.date

            binding.cardItem.setCardBackgroundColor(randomColors())

            binding.layout.setBackgroundResource(randomColors())

            binding.cardItem.setOnClickListener {

                listener?.invoke(items)

            }

            binding.cardItem.setOnLongClickListener {

                listenerEvent?.invoke(items,binding.cardItem)

                true
            }
        }
    }

    private var differCallBack=object :DiffUtil.ItemCallback<Notes>(){

        override fun areItemsTheSame(oldItem: Notes, newItem: Notes): Boolean {

            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: Notes, newItem: Notes): Boolean {

            return oldItem==newItem
        }
    }

    val differ=AsyncListDiffer(this,differCallBack)

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

    fun randomColors():Int{

        val list=ArrayList<Int>()

        list.add(R.color.teal_200)
        list.add(R.color.DarkCyan)
        list.add(R.color.MediumSpringGreen)
        list.add(R.color.Lime)
        list.add(R.color.greens)
        list.add(R.color.yellow)
        list.add(R.color.MediumTurquoise)
        list.add(R.color.Ivory)

        val seed=System.currentTimeMillis().toInt()

        val randomIndex= Random(seed).nextInt(list.size)

        return list[randomIndex]

    }
}