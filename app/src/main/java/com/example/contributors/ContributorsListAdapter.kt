package com.example.contributors

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import java.lang.NullPointerException

class ContributorsListAdapter(activity : Activity) : RecyclerView.Adapter<ContributorsListAdapter.ViewHolder>() {
    private val _activity = activity

    data class Item (
        val id : Int,
        val login : String,
        val contribs : Int
    )
    private val _items = mutableListOf<Item>()

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

            login_text.text        = _items[position].login
            contribution_text.text = _items[position].contribs.toString()
            item_layout.setOnClickListener {
                (_activity as? MainActivity)?.replaceFragment(ContributorDetailFragment().apply {
                    arguments = Bundle().apply { putInt("id", _items[position].id) }
                })
            }

        }
    }

    override fun getItemCount() = _items.size

    fun add(id: Int, contributor: Contributor) {
        _items.add(Item(
            id = id,
            login = contributor.element(ELEMENTS.LOGIN).toString(),
            contribs = contributor.element(ELEMENTS.CONTRIBUTIONS)?.toInt()?: throw NullPointerException()
        ))
    }

}