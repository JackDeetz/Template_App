package com.zybooks.templateapp

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView

class AnimationsExample : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animations_example)

        val rocketImage: ImageView = findViewById(R.id.imageView5)
        rocketImage.setBackgroundResource(R.drawable.frame_by_frame_animation)
        val rocketAnimation = rocketImage.background as AnimationDrawable

        rocketImage.setOnClickListener {
            if (rocketAnimation.isRunning) {
                rocketAnimation.stop()
            } else {
                rocketAnimation.start()
            }
        }

    }

    fun frasierHeadImageClicked(view: View) {
        val frasierHeadViewAnimation = findViewById<ImageView>(R.id.imageView9)
        val frasierHeadAnim: Animation = AnimationUtils.loadAnimation(this, R.anim.view_animation_frasier_head_instructions)
        frasierHeadViewAnimation.startAnimation(frasierHeadAnim)}

    fun valueAnimatorButtonClicked(view: View) {
        startActivity(Intent(this, ValueAnimatorExample::class.java))
    }
}