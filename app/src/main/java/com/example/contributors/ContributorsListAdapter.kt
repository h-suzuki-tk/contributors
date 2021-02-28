package com.example.contributors

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.transform.CircleCropTransformation

class ContributorsListAdapter(activity : Activity) : RecyclerView.Adapter<ContributorsListAdapter.ViewHolder>() {
    private val _activity = activity

    data class Item (
        val user : User,
        val contribs : Int
    )
    private val _items = mutableListOf<Item>()

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val avatar_img        : ImageView
        val name_text         : TextView
        val login_text        : TextView
        val contribution_text : TextView
        val item_layout       : LinearLayout

        init {
            avatar_img        = view.findViewById(R.id.avatar)
            name_text         = view.findViewById(R.id.name)
            login_text        = view.findViewById(R.id.login)
            contribution_text = view.findViewById(R.id.contribs)
            item_layout       = view.findViewById(R.id.row)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.contributor_row_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {

            avatar_img.load(_items[position].user.element(ELEMENTS.AVATAR_URL)) {
                transformations(CircleCropTransformation())
            }
            name_text.text         = _items[position].user.element(ELEMENTS.NAME) ?: "No name"
            login_text.text        = _items[position].user.element(ELEMENTS.LOGIN)
            contribution_text.text = _items[position].contribs.toString()
            item_layout.setOnClickListener {
                (_activity as? MainActivity)?.replaceFragment(UserDetailFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable("user", _items[position].user)
                    }
                })
            }

        }
    }

    override fun getItemCount() = _items.size

    fun add(user : User, contribs : Int) {
        _items.add(Item(user, contribs))
    }

}