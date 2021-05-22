package com.example.memoapp.data.adapters

import android.content.Context
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.memoapp.R
import com.example.memoapp.data.Memo


class Adapter(
    var context: Context,
    var notes: List<Memo>,
    var listener: OnItemClickListener
) : RecyclerView.Adapter<Adapter.NotesHolder>() {

    inner class NotesHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener, View.OnCreateContextMenuListener {
        val noteTitle: TextView = itemView.findViewById(R.id.note_title)
        val noteDesc: TextView = itemView.findViewById(R.id.note_desc)
        private val noteDelete: ImageView = itemView.findViewById(R.id.note_delete)
        private val noteEdit: ImageView = itemView.findViewById(R.id.note_edit)
        private val parent: CardView = itemView.findViewById(R.id.parent)

        init {
            parent.setOnClickListener(this)
            noteDelete.setOnClickListener(this)
            noteEdit.setOnClickListener(this)
            itemView.setOnCreateContextMenuListener(this)
            itemView.setOnLongClickListener {
                it.showContextMenu()
            }
        }

        override fun onClick(v: View?) {
            when (v?.id) {
                R.id.parent -> listener.onItemClick(adapterPosition)
                R.id.note_delete -> Toast.makeText(
                    context,
                    "This function currently is in context menu",
                    Toast.LENGTH_SHORT
                ).show()
                R.id.note_edit -> Toast.makeText(
                    context,
                    "This function currently is in context menu",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            TODO("Not yet implemented")
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.memo_item, parent, false)
        return NotesHolder(view)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: NotesHolder, position: Int) {
        holder.itemView.apply {
            holder.noteTitle.text = notes[position].title
            holder.noteDesc.text = notes[position].description
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
        fun onDeleteClick(position: Int)
        fun onEditClick(position: Int)
    }
}
