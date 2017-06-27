package com.agilie.agmobilegiftinterface

import android.app.Activity
import android.content.Context
import com.agilie.agmobilegiftinterface.shake.ShakeBuilder


interface InterfaceInteractor {
    fun shake(activity: Activity): ShakeBuilder.Builder
}