package com.example.contributors

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class ContributorsListAdapter : RecyclerView.Adapter<ContributorsListAdapter.ViewHolder>() {

    private val _contributors = mutableListOf<Contributor>()

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val login_text        : TextView
        val contribution_text : TextView
        val item_layout       : LinearLayout

        init {
            login_text        = view.findViewById(R.id.text_view)
            contribution_text = view.findViewById(R.id.text_view2)
            item_layout       = view.findViewById(R.id.item_layout)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.contributor_row_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {

            login_text.text        = _contributors[position].element(ELEMENTS.LOGIN)
            contribution_text.text = _contributors[position].element(ELEMENTS.CONTRIBUTIONS)
            item_layout.setOnClickListener {
                Log.d("確認", "押されました")
            }

        }
    }

    override fun getItemCount() = _contributors.size

    fun add(contributor: Contributor) {
        _contributors.add(contributor)
    }

}