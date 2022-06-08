package com.restauran.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

class RoundishImageView(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    AppCompatImageView(context, attrs, defStyleAttr) {

    val CORNER_NONE = 0
    val CORNER_TOP_LEFT = 1
    val CORNER_TOP_RIGHT = 2
    val CORNER_BOTTOM_RIGHT = 4
    val CORNER_BOTTOM_LEFT = 8
    val CORNER_ALL = 15


    private var cornerRect = RectF()
    private var path = Path()
    private var cornerRadius = 0
    private var topLeftCornerRaduis = 0
    private var bottomLeftCornerRaduis = 0
    private var topRightCornerRaduis = 0
    private var bottomRightCornerRaduis = 0
    private var roundedCorners = 0

    fun setCornerRadius(radius: Int) {
        cornerRadius = radius
        setPath()
        invalidate()
    }

    fun getRadius(): Int {
        return cornerRadius
    }

    fun setRoundedCorners(corners: Int) {
        roundedCorners = corners
        setPath()
        invalidate()
    }

    fun getRoundedCorners(): Int {
        return roundedCorners
    }

    fun getTopLeftCornerRaduis(): Int {
        return topLeftCornerRaduis
    }

    fun setTopLeftCornerRaduis(topLeftCornerRaduis: Int) {
        this.topLeftCornerRaduis = topLeftCornerRaduis
        setPath()
        invalidate()
    }

    fun getBottomLeftCornerRaduis(): Int {
        return bottomLeftCornerRaduis
    }

    fun setBottomLeftCornerRaduis(bottomLeftCornerRaduis: Int) {
        this.bottomLeftCornerRaduis = bottomLeftCornerRaduis
        setPath()
        invalidate()
    }

    fun getTopRightCornerRaduis(): Int {
        return topRightCornerRaduis
    }

    fun setTopRightCornerRaduis(topRightCornerRaduis: Int) {
        this.topRightCornerRaduis = topRightCornerRaduis
        setPath()
        invalidate()
    }

    fun getBottomRightCornerRaduis(): Int {
        return bottomRightCornerRaduis
    }

    fun setBottomRightCornerRaduis(bottomRightCornerRaduis: Int) {
        this.bottomRightCornerRaduis = bottomRightCornerRaduis
        setPath()
        invalidate()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        setPath()
    }

    override fun onDraw(canvas: Canvas?) {
        if (!path.isEmpty) {
            canvas?.clipPath(path)
        }
        super.onDraw(canvas)
    }

    private fun setPath() {
        path.rewind()
        if (cornerRadius >= 1f && roundedCorners != CORNER_NONE) {
            val width = width
            val height = height
            val twoRadius = (cornerRadius * 2).toFloat()
            cornerRect[-cornerRadius.toFloat(), -cornerRadius.toFloat(), cornerRadius.toFloat()] =
                cornerRadius.toFloat()
            if (isRounded(CORNER_TOP_LEFT)) {
                cornerRect.offsetTo(0f, 0f)
                path.arcTo(cornerRect, 180f, 90f)
            } else {
                path.moveTo(0f, 0f)
            }
            if (isRounded(CORNER_TOP_RIGHT)) {
                cornerRect.offsetTo(width - twoRadius, 0f)
                path.arcTo(cornerRect, 270f, 90f)
            } else {
                path.lineTo(width.toFloat(), 0f)
            }
            if (isRounded(CORNER_BOTTOM_RIGHT)) {
                cornerRect.offsetTo(width - twoRadius, height - twoRadius)
                path.arcTo(cornerRect, 0f, 90f)
            } else {
                path.lineTo(width.toFloat(), height.toFloat())
            }
            if (isRounded(CORNER_BOTTOM_LEFT)) {
                cornerRect.offsetTo(0f, height - twoRadius)
                path.arcTo(cornerRect, 90f, 90f)
            } else {
                path.lineTo(0f, height.toFloat())
            }
            path.close()
        }
    }

    private fun isRounded(corner: Int): Boolean {
        return roundedCorners and corner == corner
    }
}