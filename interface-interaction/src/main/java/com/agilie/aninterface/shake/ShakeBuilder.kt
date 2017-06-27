package com.agilie.agmobilegiftinterface.shake

import android.R
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnticipateOvershootInterpolator

/**
 *Action Flow:
 * 1) Shake all activity
 * - create AnimatorSet for all views
 * - save AnimatorSet in List
 * 2) Stop shake
 * - cancel animation for all views
 */

class ShakeBuilder() {

    lateinit var activity: Activity
        private set

    var view: View? = null

    private var viewsList: List<View>? = null
    private var animatorList = ArrayList<AnimatorSet>()
    private var animation = true

    private constructor (builder: Builder) : this() {
        view = builder.view
        activity = builder.activity
    }

    fun shakeMyView() {
        clearViewAnimation(view)
        shake(view)
    }

    fun shakeMyActivity() {
        if (!animation) {
            return
        }
        viewsList = getAllChildren(activity?.window?.decorView?.findViewById(R.id.content))
        viewsList?.forEach {
            shake(it)
        }
        animation = false
    }

    fun stopAnimation() {
        animatorList.forEach {
            it.cancel()
        }
        clearViewAnimation(view)
        viewsList?.forEach { clearViewAnimation(it) }
        animation = true
    }

    private fun getAllChildren(view: View?): List<View> {

        if (view !is ViewGroup) {
            val viewArrayList = ArrayList<View>()
            view?.let { viewArrayList.add(it) }
            return viewArrayList
        }

        val result = ArrayList<View>()

        (0..view.childCount - 1)
                .map { view.getChildAt(it) }
                .forEach {
                    val viewArrayList = ArrayList<View>()
                    viewArrayList.addAll(getAllChildren(it))
                    result.addAll(viewArrayList)
                }

        return result.distinct()
    }

    private fun shake(view: View?) {
        animatorList.add(createAnimatorSet(view))

    }

    private fun createAnimatorSet(view: View?) =
            AnimatorSet().apply {
                play(shakeAnimator(view, "rotation", -5f, 5f, 0f, 100))
                        .with(shakeAnimator(view, "translate", -5f, 5f, 0f, 100))
                        .with(shakeAnimator(view, "scaleX", 1f, 1.1f, 1f, 300))
                        .with(shakeAnimator(view, "scaleY", 1f, 1.1f, 1f, 300))
                start()
            }

    private fun shakeAnimator(view: View?, propertyName: String, v1: Float,
                              v2: Float, v3: Float,
                              d: Long) =
            ObjectAnimator.ofFloat(view, propertyName, v1, v2, v3).apply {
                repeatCount = ValueAnimator.INFINITE
                duration = d
                interpolator = AnticipateOvershootInterpolator()
            }

    private fun clearViewAnimation(view: View?) {
        view?.apply {
            alpha = 1f
            scaleX = 1f
            scaleY = 1f
            translationX = 1f
            translationY = 1f
            rotation = 0f
            rotationX = 0f
            rotationY = 0f
        }
    }

    class Builder(val activity: Activity) {

        constructor(activity: Activity, view: View) : this(activity) {
            this.view = view
        }

        var view: View? = null
            private set

        fun setView(view: View): Builder {
            this.view = view
            return this
        }

        fun build() = ShakeBuilder(this)

    }
}