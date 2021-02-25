package com.example.contributors

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class ContributorsListAdapter(private val dataset : Array<String>) : RecyclerView.Adapter<ContributorsListAdapter.ViewHolder>() {

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val text_view : TextView

        init {
            text_view = view.findViewById(R.id.text_view)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.contributor_row_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            text_view.text = dataset[position]
            text_view.setOnClickListener {
                Log.d("確認", "押されました")
            }
        }
    }

    override fun getItemCount() = dataset.size

}