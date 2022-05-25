package com.zybooks.templateapp

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView

const val RADIUS = 100f
const val BALL_COLOR = -0x555501

class SurfaceViewExample(context: Context, attrs: AttributeSet) :
SurfaceView(context, attrs), SurfaceHolder.Callback {

    private lateinit var animThread: AnimationThread

    init {
        holder.addCallback(this)
    }

    override fun surfaceCreated(p0: SurfaceHolder) {
        TODO("Not yet implemented")
    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
        TODO("Not yet implemented")
    }

    override fun surfaceDestroyed(p0: SurfaceHolder) {
        TODO("Not yet implemented")
    }
}

class AnimationThread(private val surfaceHolder: SurfaceHolder) : Thread() {

    private val ball: Ball
    private var threadRunning = false

    init {
        threadRunning = true

        // Create a ball with boundaries determined by canvas's size
        val canvas = surfaceHolder.lockCanvas()
        ball = Ball(canvas.width, canvas.height)
        surfaceHolder.unlockCanvasAndPost(canvas)
    }

    override fun run() {
        var previousFrameTime = System.currentTimeMillis()
        while (threadRunning) {
            val canvas = surfaceHolder.lockCanvas()
            canvas?.let {

                // Wipe canvas clean
                it.drawRGB(255, 255, 255)

                // Move and draw ball
                ball.move()
                ball.draw(it)

                surfaceHolder.unlockCanvasAndPost(it)
            }
        }
    }

    fun stopThread() {
        threadRunning = false
    }
}

class Ball(private val surfaceWidth: Int, private val surfaceHeight: Int) {

    private var paint = Paint()
    private var center = PointF(100f, 100f)
    private var velocity = PointF(10f, 10f)

    init {
        paint.color = BALL_COLOR
    }

    fun move() {

        // Add velocity to ball's center
        center.offset(velocity.x, velocity.y)

        // Check for top and bottom collisions
        if (center.y > surfaceHeight - RADIUS) {
            center.y = surfaceHeight - RADIUS
            velocity.y *= -1
        } else if (center.y < RADIUS) {
            center.y = RADIUS
            velocity.y *= -1
        }

        // Check for right and left collisions
        if (center.x > surfaceWidth - RADIUS) {
            center.x = surfaceWidth - RADIUS
            velocity.x *= -1
        } else if (center.x < RADIUS) {
            center.x = RADIUS
            velocity.x *= -1
        }
    }

    fun draw(canvas: Canvas) {
        canvas.drawCircle(center.x, center.y, RADIUS, paint)
    }
}