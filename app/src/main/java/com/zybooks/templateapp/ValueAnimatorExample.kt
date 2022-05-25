package com.zybooks.templateapp

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import kotlinx.coroutines.delay

class ValueAnimatorExample : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_value_animator_example)

    }

    fun frasierHeadValueAnimatorImageClicked(view: View) {
        val frasierHead = findViewById<ImageView>(R.id.imageView11)
        val animator = ValueAnimator.ofFloat(0f, 360f)
        animator.duration = 2000
        animator.interpolator = LinearInterpolator()

        animator.addUpdateListener { animation: ValueAnimator ->
            frasierHead.rotation = animation.animatedValue as Float
        }

        animator.start()
    }

    fun frasierHeadObjectAnimatorImageClicked(view: View) {
        val frasierHead = findViewById<ImageView>(R.id.imageView13)
        val animator = ObjectAnimator.ofFloat(frasierHead, "rotation", 0f, 360f)

        animator.interpolator = LinearInterpolator()
        animator.duration = 1000
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.repeatCount = 2
        animator.start()
    }

    fun frasierHeadObjectAnimatorSourcesImageClicked(view: View) {
        // Load and start the animation resource
        val frasierHead = findViewById<ImageView>(R.id.imageView15)
        val animator = AnimatorInflater.loadAnimator(this, R.animator.object_and_value_resource_animation_instructions_frasier_head) as ObjectAnimator
        animator.target = frasierHead
        animator.start()
    }

    fun animatorSetButtonClicked(view: View) {
        val frasierHeadOne = findViewById<ImageView>(R.id.imageView11)
        val frasierHeadTwo = findViewById<ImageView>(R.id.imageView13)
        val frasierHeadThree = findViewById<ImageView>(R.id.imageView15)
        val rotateBison = ObjectAnimator.ofFloat(frasierHeadOne, "rotationX", 180f)
        rotateBison.duration = 300

        val scaleBison = ObjectAnimator.ofFloat(frasierHeadTwo, "scaleX", 1.5f)
        scaleBison.duration = 700

        val moveBison = ObjectAnimator.ofFloat(frasierHeadThree, "translationX", -200f)
        moveBison.duration = 1500

        val hideBison = ObjectAnimator.ofFloat(frasierHeadOne, "alpha", 1f, 0f)
        hideBison.duration = 300

        val showBison = ObjectAnimator.ofFloat(frasierHeadOne, "alpha", 0f, 1f)
        showBison.duration = 300
        val animSet = AnimatorSet()

        animSet.play(rotateBison).with(scaleBison)
        animSet.play(moveBison).after(scaleBison)
        animSet.play(hideBison).after(moveBison)
        animSet.play(showBison).after(moveBison)
        animSet.start()
    }
    fun animatorSetButtonSourceFileClicked(view: View) {

        val frasierHead = findViewById<ImageView>(R.id.imageView15)

        // Load and start the animation resource
        val animator = AnimatorInflater.loadAnimator(
            this, R.animator.animator_set_animation_instructions) as AnimatorSet
        animator.setTarget(frasierHead)
        animator.start()
    }


}