package com.henra.perqaragame.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SpaceDecoration(
    private val space: Int,
    private val orientation: Int,
    private val start: Int = 0,
    private val end: Int = 0,
    private val side: Int = 0
) : RecyclerView.ItemDecoration() {
    companion object {
        const val VERTICAL = 1
        const val HORIZONTAL = 2
    }

    constructor(space: Int, orientation: Int = VERTICAL, margin: Int) : this(
        space,
        orientation,
        margin,
        margin,
        margin
    )

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        when {
            parent.getChildAdapterPosition(view) == 0 -> {
                firstItem(outRect)
            }
            parent.getChildAdapterPosition(view) == parent.adapter?.itemCount?.minus(1) -> {
                lastItem(outRect)
            }
            else -> {
                midItem(outRect)
            }
        }
    }

    private fun firstItem(outRect: Rect) {
        setOutRect(outRect, start, 0)
    }

    private fun midItem(outRect: Rect) {
        setOutRect(outRect, space, 0)
    }

    private fun lastItem(outRect: Rect) {
        setOutRect(outRect, space, end)
    }

    private fun setOutRect(outRect: Rect, first: Int, second: Int) {
        if (orientation == HORIZONTAL) {
            outRect.left = first
            outRect.right = second
            outRect.top = side
            outRect.bottom = side
        } else {
            outRect.top = first
            outRect.bottom = second
            outRect.left = side
            outRect.right = side
        }
    }
}
