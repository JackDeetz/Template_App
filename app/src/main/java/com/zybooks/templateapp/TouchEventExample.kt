package com.zybooks.templateapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout

class TouchEventExample() : AppCompatActivity() {

    private lateinit var textViewTouchEventExample: TextView
    private lateinit var activityContainer : ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_touch_event_example)

        activityContainer = findViewById(R.id.constraintLayoutTouchEventActivity)
        textViewTouchEventExample = findViewById(R.id.textViewTouchEvent)
    }

    @SuppressLint("ClickableViewAccessibility") //uknown whats up with this
    override fun onTouchEvent(event: MotionEvent): Boolean {
//        val action = when (event.action) {
//            MotionEvent.ACTION_DOWN -> "ACTION_DOWN" //touch engaged
//            MotionEvent.ACTION_MOVE -> "ACTION_MOVE"  //touch move
//            MotionEvent.ACTION_UP -> "ACTION_UP"      //touch ends
//            else -> "ACTION_CANCEL"
//        }
//        if (event.action == "Action" > 500) {
//            Toast.makeText(this, "$action x = ${event.x} y = ${event.y}", Toast.LENGTH_SHORT).show()

        var xDown = 0
        var yDown = 0
        activityContainer.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    xDown = event.x.toInt()
                    yDown = event.y.toInt()
                    true
                }
                MotionEvent.ACTION_UP -> {
                    if (((event.x.toInt()) > xDown + 200) and ((event.y.toInt()) < yDown - 200)) {
                        textViewTouchEventExample.text = "MotionEvent- Right and Up-ward Gesture\n"
                    }
                    else if (((event.x.toInt()) > xDown + 200) and ((event.y.toInt()) > yDown + 200)) {
                        textViewTouchEventExample.text = "MotionEvent- Right and Down-ward Gesture\n"
                    }
                    else if (((event.x.toInt()) < xDown - 200) and ((event.y.toInt()) < yDown - 200)) {
                        textViewTouchEventExample.text = "MotionEvent- Left and Up-ward Gesture\n"
                    }
                    else if (((event.x.toInt()) < xDown - 200) and ((event.y.toInt()) > yDown + 200)) {
                        textViewTouchEventExample.text = "MotionEvent- Left and Down-ward Gesture\n"
                    }
                    else if ((event.x.toInt()) > xDown + 200) {
                        textViewTouchEventExample.text = "MotionEvent- Rightward Gesture\n"
                    }
                    else if ((event.x.toInt()) < xDown - 200) {
                        textViewTouchEventExample.text = "MotionEvent- Leftward Gesture\n"
                    }
                    else if ((event.y.toInt()) < yDown - 200) {
                        textViewTouchEventExample.text = "MotionEvent- Upward Gesture\n"
                    }
                    else if ((event.y.toInt()) > yDown + 200) {
                        textViewTouchEventExample.text = "MotionEvent- Downward Gesture\n"
                    }
                    else {
                        textViewTouchEventExample.text = ""
                    }
                    textViewTouchEventExample.text =  textViewTouchEventExample.text.toString() + ("ACTION_DOWN X : ${xDown} Y : ${yDown} \n" +
                          "ACTION_UP X : ${event.x.toInt()} Y : ${event.y.toInt()}")
                    true
                }
                else -> {
                    true
                }
            }
        }
        return true
    }
}