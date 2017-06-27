package com.agilie.agmobilegiftinterface

import android.app.Activity
import com.agilie.agmobilegiftinterface.shake.ShakeBuilder

class InterfaceInteractorImpl : InterfaceInteractor {

    override fun shake(activity: Activity) = ShakeBuilder.Builder(activity)
}

