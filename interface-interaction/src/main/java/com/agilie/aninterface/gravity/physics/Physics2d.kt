package com.agilie.agmobilegiftinterface.gravity.physics

import android.view.View
import android.view.ViewGroup
import com.agilie.aninterface.interface_interaction.R
import org.jbox2d.collision.shapes.PolygonShape
import org.jbox2d.common.Vec2
import org.jbox2d.dynamics.*
import java.util.*

class Physics2d {

    companion object {
        val VELOCITY = 10
        val POSITION = 1
        val FRAME_TIME = 1 / 60f
        val WORLD_X = 0.0f
        val WORLD_Y = 0.0f
        val PIXELS_METR = 50f
        val BOUND_SIZE = 0f
        val FILTER = 1f
        val GRAVITY_SCALE = 2
        val ACCELERATION = 0.5f
    }

    private val viewGroup: ViewGroup
    private var world: World? = null
    private val bodies = ArrayList<Body>()
    private var enablePhysics = true
    private var density: Float
    private var width = 0
    private var height = 0

    constructor(viewGroup: ViewGroup) {
        this.viewGroup = viewGroup
        density = viewGroup.resources.displayMetrics.density
    }

    fun enablePhysics() {
        enablePhysics = true
    }

    fun disablePhysics() {
        enablePhysics = false
        viewGroup.invalidate()
    }

    fun onSizeChanged(width: Int, height: Int) {
        this.width = width
        this.height = height
    }


    fun onLayout() = initWorld()

    fun updateWorldEntity() {
        if (!enablePhysics) {
            return
        }
        world?.step(FRAME_TIME, VELOCITY, POSITION)
        for (i in 0..viewGroup.childCount - 1) {
            val view = viewGroup.getChildAt(i)
            val body = view.getTag(R.id.physics_tag)

            if (body != null) {
                body as Body
                view.apply {
                    x = metersToPixels(body.position.x) - width / 2
                    y = metersToPixels(body.position.y) - height / 2
                    rotation = radiansToDegrees(body.angle) % 360
                }
            }
        }
        viewGroup.invalidate()
    }

    private fun initWorld() {
        world = World(Vec2(WORLD_X, WORLD_Y))
        createTopAndBottomWalls()
        createLeftAndRightWalls()
        for (i in 0..viewGroup.childCount - 1) {
            var body = viewGroup.getChildAt(i).getTag(R.id.physics_tag)
            createBody(viewGroup.getChildAt(i), body)
        }
    }

    private fun createTopAndBottomWalls() {
        val boundSize = Math.round(BOUND_SIZE)
        val boxWidth = pixelsToMeters(width)
        val boxHeight = pixelsToMeters(boundSize)
        val fixtureDef = createFixtureDef(boxWidth, boxHeight)
        val bodyDef = BodyDef()
                .apply {
                    type = BodyType.STATIC
                    position.set(0f, -boxHeight)
                }

        val topWall = world?.createBody(bodyDef)
        topWall?.createFixture(fixtureDef)
        topWall?.let { bodies.add(it) }

        bodyDef.position.set(0f, pixelsToMeters(height) + boxHeight)
        val bottomWall = world?.createBody(bodyDef)
        bottomWall?.createFixture(fixtureDef)
        bottomWall?.let { bodies.add(it) }
    }

    private fun createLeftAndRightWalls() {
        val boundSize = Math.round(BOUND_SIZE)
        val boxWidth = pixelsToMeters(boundSize)
        val boxHeight = pixelsToMeters(height)
        val bodyDef = BodyDef()
                .apply {
                    position.set(-boxWidth, 0f)
                    type = BodyType.STATIC
                }
        val fixtureDef = createFixtureDef(boxWidth, boxHeight)

        val leftWall = world?.createBody(bodyDef)
        leftWall?.createFixture(fixtureDef)
        leftWall?.let { bodies.add(it) }

        bodyDef.position.set(pixelsToMeters(width) + boxWidth, 0f)
        val rightWall = world?.createBody(bodyDef)
        rightWall?.createFixture(fixtureDef)
        rightWall?.let { bodies.add(it) }
    }

    private fun createFixtureDef(boxWidth: Float, boxHeight: Float): FixtureDef {
        val box = PolygonShape()
        box.setAsBox(boxWidth, boxHeight)
        val fixtureDef = FixtureDef()
                .apply {
                    shape = box
                    density = 1f
                }
        return fixtureDef
    }

    private fun createBody(view: View, oldBody: Any?) {
        val bodyDef = createBodyDef()
        bodyDef.position.set(
                pixelsToMeters(view.x + view.width / 2),
                pixelsToMeters(view.y + view.height / 2))

        if (oldBody != null) {
            oldBody as Body
            bodyDef.apply {
                angle = oldBody.angle
                angularVelocity = oldBody.angularVelocity
                linearVelocity = oldBody.linearVelocity
                angularDamping = oldBody.angularDamping
                linearDamping = oldBody.linearDamping
            }
        } else {
            bodyDef.angularVelocity = degreesToRadians(view.rotation)
        }

        val fixtureDef = createFixtureDef(0f, 0f)
        fixtureDef.shape = createBoxShape(view)

        val body = world!!.createBody(bodyDef)
        body.createFixture(fixtureDef)
        view.setTag(R.id.physics_tag, body)
    }

    private fun createBodyDef(): BodyDef {
        val bodyDef = BodyDef()
        bodyDef.type = BodyType.DYNAMIC
        return bodyDef
    }

    private fun createBoxShape(view: View): PolygonShape {
        val shape = PolygonShape()
        val boxWidth = pixelsToMeters(view.width / 2)
        val boxHeight = pixelsToMeters(view.height / 2)
        shape.setAsBox(boxWidth, boxHeight)
        return shape
    }

    private var fx = 0f
    private var fy = 0f

    internal fun onStartGravity(sensorX: Float, sensorY: Float) {
        fx = (sensorX * FILTER + fx * (1 - FILTER)) * (-GRAVITY_SCALE)
        fy = (sensorY * FILTER + fy * (1 - FILTER)) * GRAVITY_SCALE

        for (i in 0..viewGroup.childCount - 1) {
            var body = viewGroup.getChildAt(i).getTag(R.id.physics_tag)
            if (body != null) {
                var impulse = Vec2(fx * ACCELERATION, fy * ACCELERATION)
                (body as Body).world.gravity = impulse
            }
        }
    }
}