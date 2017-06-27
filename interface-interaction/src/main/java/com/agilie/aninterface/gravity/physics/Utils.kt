package com.agilie.agmobilegiftinterface.gravity.physics

fun metersToPixels(meters: Float): Float {
    return meters * Physics2d.PIXELS_METR
}

fun pixelsToMeters(pixels: Float): Float {
    return pixels / Physics2d.PIXELS_METR
}

fun pixelsToMeters(pixels: Int): Float {
    return pixels / Physics2d.PIXELS_METR
}

fun radiansToDegrees(radians: Float): Float {
    return radians / 3.14f * 180f
}

fun degreesToRadians(degrees: Float): Float {
    return degrees / 180f * 3.14f
}