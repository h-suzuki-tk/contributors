package com.example.contributors

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ItemDecoration(
    private val context : Context,
    private val space : Int = UNSPECIFIED) : RecyclerView.ItemDecoration() {

    companion object {
        private const val UNSPECIFIED = -1
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        outRect.bottom = when ( space == UNSPECIFIED ) {
            true  -> context.resources.getDimensionPixelSize(R.dimen.item_margin)
            false -> space
        }
    }

}