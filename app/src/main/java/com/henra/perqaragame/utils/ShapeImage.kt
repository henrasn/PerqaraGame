package com.henra.perqaragame.utils

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.imageview.ShapeableImageView

class ShapeImage(context: Context, attrs: AttributeSet?) : ShapeableImageView(context, attrs) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = measuredWidth
        val newHeight = (3 * width) / 4
        setMeasuredDimension(width, newHeight)
    }
}