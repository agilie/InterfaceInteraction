package com.agilie.agmobilegiftinterface.gravity.physics.view

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.agilie.agmobilegiftinterface.gravity.physics.Physics2d

class Physics2dViewGroup : ViewGroup {

    var physics2d: Physics2d? = null

    constructor(context: Context) : super(context) {
        initPhysics2d()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initPhysics2d()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        physics2d?.onSizeChanged(w, h)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        physics2d?.onLayout()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        physics2d?.updateWorldEntity()
    }

    override fun generateLayoutParams(attrs: AttributeSet): RelativeLayout.LayoutParams {
        return RelativeLayout.LayoutParams(context, attrs)
    }

    private fun initPhysics2d() {
        setWillNotDraw(false)
        physics2d = Physics2d(this)
    }
}