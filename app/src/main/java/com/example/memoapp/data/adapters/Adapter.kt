package com.example.memoapp.data.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.memoapp.R
import com.example.memoapp.data.Memo

class  Adapter(var memoList: List<Memo>, var listener: OnItemClickListener) :
    RecyclerView.Adapter< Adapter.ViewHolder>() {


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val itemTitle: TextView = view.findViewById(R.id.itemTitle)
        val itemDescription: TextView= view.findViewById(R.id.itemDescription)

        private val parent: CardView = view.findViewById(R.id.parent)
        private val delete: ImageView = view.findViewById(R.id.delete)
        private val edit : ImageView = view.findViewById(R.id.edit)






        init {
            parent.setOnClickListener(this)
            delete.setOnClickListener(this)
            edit.setOnClickListener(this)

        }

        override fun onClick(v: View?) {
            when(v?.id){
                R.id.parent -> listener.onItemClick(adapterPosition)
                R.id.edit -> listener.onEditClick(adapterPosition)
                R.id.delete -> listener.onDeleteClick(adapterPosition)
            }
        }
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.memo_item, viewGroup, false)
        return ViewHolder(view)




    }


    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.apply { viewHolder.itemTitle.text = memoList[position].memoTitle
                                   viewHolder.itemDescription.text = memoList[position].memoDescription

        }
    }

    override fun getItemCount() = memoList.size



    interface OnItemClickListener{
        fun onItemClick(position: Int)
        fun onEditClick(position: Int)
        fun onDeleteClick(position: Int)

    }

}
