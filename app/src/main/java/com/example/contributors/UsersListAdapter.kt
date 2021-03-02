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

class UsersListAdapter(activity : Activity) : RecyclerView.Adapter<UsersListAdapter.ViewHolder>() {
    private val _activity = activity

    private val _items = mutableListOf<User>()

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val avatar_img        : ImageView
        val name_text         : TextView
        val login_text        : TextView
        val item_layout       : LinearLayout

        init {
            avatar_img        = view.findViewById(R.id.avatar)
            name_text         = view.findViewById(R.id.name)
            login_text        = view.findViewById(R.id.login)
            item_layout       = view.findViewById(R.id.row)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.user_row_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {

            avatar_img.load(_items[position].element(ELEMENTS.AVATAR_URL)) {
                transformations(CircleCropTransformation())
            }
            name_text.text         = _items[position].element(ELEMENTS.NAME) ?: "No name"
            login_text.text        = _items[position].element(ELEMENTS.LOGIN)
            item_layout.setOnClickListener {
                (_activity as? MainActivity)?.replaceFragment(UserDetailFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable("user", _items[position])
                    }
                })
            }

        }
    }

    override fun getItemCount() = _items.size

    fun add(user : User) {
        _items.add(user)
    }

}